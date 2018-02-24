package com.pplive.bip.report;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;


public class AlterColumn {
    Pair<List<ResultColumn>, List<ResultColumn> > alter;

    public AlterColumn(List<ResultColumn> addColumns, List<ResultColumn> modifyColumns) {
        alter = ImmutablePair.of(addColumns, modifyColumns);
    }

    public static AlterColumn of(List<ResultColumn> addColumns, List<ResultColumn> modifyColumns) {
        return new AlterColumn(addColumns, modifyColumns);
    }

    public List<ResultColumn> getAddColumns() {
        return alter.getLeft();
    }

    public List<ResultColumn> getModifyColumns() {
        return alter.getRight();
    }
}
