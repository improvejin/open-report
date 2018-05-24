package com.pplove.bip.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplove.bip.exception.SerializeException;
import org.w3c.dom.Document;

import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
	private SerializeUtil() {
	}

	private static final String utf8 = "UTF-8";

	public static <T> void xmlSerialize(T obj, String filePath) {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(filePath), utf8);
			xmlSerialize(obj, writer);
		}catch (FileNotFoundException e) {
			throw new SerializeException(String.format("File %s Not Found Exception",filePath), e);
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("Property Exception", e);
		} finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					
				}
		}

	}

	public static <T> void xmlSerialize(T obj, File file) {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file), utf8); // new FileWriter(file);
			xmlSerialize(obj, writer);
		}  catch (FileNotFoundException e) {
			throw new SerializeException(String.format("File %s Not Found Exception",file.getPath()), e);
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("Property Exception", e);
		} finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					
				}
		}
	}

	public static <T> void xmlSerialize(T obj, OutputStream os) {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(os, utf8);
			xmlSerialize(obj, writer);
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("Property Exception", e);
		} finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {

				}
		}
	}


	public static <T> void xmlSerialize(T obj, Writer writer) {
		if (obj == null || writer == null) {
			throw new IllegalArgumentException("obj or writer cannot be null");
		}
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, utf8);
			m.marshal(obj, writer);
		} catch (PropertyException e) {
			throw new SerializeException("Property Exception", e);
		} catch (JAXBException e) {
			throw new SerializeException("JAXB Exception", e);
		}
	}

	public static <T> String xmlSerialize(T obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj or writer cannot be null");
		}
		StringWriter writer = null;
		try {

			writer = new StringWriter();
			xmlSerialize(obj, writer);
			return writer.toString();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					
				}

		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlDeserialize(Class<T> cls, Reader reader) {
		if (cls == null || reader == null) {
			throw new IllegalArgumentException("cls or reader cannot be null");
		}
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(cls);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new SerializeException("JAXB Exception", e);
		}
	}

	public static <T> T xmlDeserialize(Class<T> cls, String filepath) {
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(filepath), utf8);
			return xmlDeserialize(cls, reader);
		}  catch (FileNotFoundException e) {
			throw new SerializeException(String.format("File %s Not Found Exception",filepath), e);
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("Property Exception", e);
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					
				}
		}
	}

	public static <T> T xmlDeserialize(Class<T> cls, File file) {
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), utf8);
			return xmlDeserialize(cls, reader);
		} catch (FileNotFoundException e) {
			throw new SerializeException(String.format("File %s Not Found Exception",file.getPath()), e);
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("Property Exception", e);
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					
				}
		}
	}

	public static <T> T xmlDeserializeContent(Class<T> cls, String xmlContext) {
		Reader reader = new StringReader(xmlContext);
		try {
			return xmlDeserialize(cls, reader);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public static void generateSchema(Class<?> cls, String filename){
		JAXBContext context;
		final List<DOMResult> results = new ArrayList<DOMResult>();
		try {
			context = JAXBContext.newInstance(cls);
			context.generateSchema(new SchemaOutputResolver() {

				@Override
				public Result createOutput(String namespaceUri, String suggestedFileName)
						throws IOException {
					DOMResult result = new DOMResult();
					result.setSystemId(suggestedFileName);
		            results.add(result);
		            return result;
				}
			});
			DOMResult domResult = results.get(0);
		    Document doc = (Document) domResult.getNode();
		    Source source = new DOMSource(doc);

	        // Prepare the output file
	        File file = new File(filename);
	        Result result = new StreamResult(file);

	        // Write the DOM document to the file
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(source, result);
		} catch (JAXBException e) {
			throw new SerializeException("JAXB Exception", e);
		}
		catch (IOException e) {
			throw new SerializeException("JAXB Exception", e);
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * JSON 序列化工具,将 Obj 对象序列化成字符串.
	 * @param obj 序列化的对象
	 * @param <T> 序列化对象的类型
	 * @return 如果对象对null,返回空串,否则,返回序列化的字符串.
	 * @throws IOException 序列化失败时,抛出IO异常
     */
	public static <T> String jsonSerialize(T obj) throws IOException {
		if (obj == null) {
			return "";
		}
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(obj);
	}

    /**
     * JSON 反序列化工具,将String形式的字符串反序列成对象 T. <b>如果遇到不可以解析的field,将会跳过</b>
     * @param obj 反序列化的对象
     * @param jsonStr 需要序列化的字符串
     * @param <T> 反序列化的对象类型
     * @return 反序列化的对象, 如果序列化的字符串为空串,返回null
     * @throws IOException 反序列失败时,抛出IO异常
     */
	public static <T> T jsonDeserialize(TypeReference<T> obj, String jsonStr) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return objectMapper.readValue(jsonStr, obj);
	}

	/**
	 * JSON 反序列化工具,将String形式的字符串反序列成对象 T. <b>如果遇到不可以解析的field,将会跳过</b>
	 * @param obj Java Class类型的反序列化的对象
	 * @param jsonStr 需要序列化的字符串
	 * @param <T> 反序列化的对象类型
	 * @return 反序列化的对象, 如果序列化的字符串为空串,返回null
	 * @throws IOException 反序列失败时,抛出IO异常
	 */
	public static <T> T jsonDeserialize(Class<T> obj, String jsonStr) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return objectMapper.readValue(jsonStr, obj);
	}

}
