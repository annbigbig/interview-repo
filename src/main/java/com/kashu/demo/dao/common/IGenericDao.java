package com.kashu.demo.dao.common;

import java.io.Serializable;

public interface IGenericDao<T extends Serializable> extends IOperations<T> {
    //
}

