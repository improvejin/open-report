package com.pplove.bip.exception;

import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.response.RestResponse;
import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ApiHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private final static Logger LOG = LoggerFactory.getLogger(ApiHandlerExceptionResolver.class);

    /**
     * 单测时有时无法加载此Bean
     */
    @Autowired(required = false)
    private MappingJackson2HttpMessageConverter jacksonHttpMessageConverter;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        // 异常日志统一在这里记录
        LOG.error(e.getMessage(), e);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader("Cache-Control", "no-cache, must-revalidate");

        RestResponse<RuntimeException> rsp = new RestResponse(null);
        if(e instanceof ApiException) {
            ApiException ex = (ApiException)e;
            rsp.setErrorCode(ex.getErrorCode());
            rsp.setErrorMessage(ex.getMessage());
        } else if (e instanceof IllegalArgumentException || e instanceof MissingServletRequestParameterException) {
            rsp.setErrorCode(ErrorCode.INVALID_ARGUMENT);
            rsp.setErrorMessage(e.getMessage());
        }else {
            rsp.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            rsp.setErrorMessage(e.getMessage());
        }

        try {
            jacksonHttpMessageConverter.write(rsp, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
        } catch (IOException e1) {
            LOG.error("Error rendering json response!", e);
        }

        return new ModelAndView();
    }
}
