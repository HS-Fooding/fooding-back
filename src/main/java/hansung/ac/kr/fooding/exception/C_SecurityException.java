package hansung.ac.kr.fooding.exception;

import hansung.ac.kr.fooding.var.CError;

public class C_SecurityException extends Exception{
    public C_SecurityException(CError cError){
        super(cError.getMessage());
    }

    public C_SecurityException(CError cError, String information){
        super(cError.getMessage() + information);
    }

    public C_SecurityException(CError cError, Throwable cause){
        super(cError.getMessage(), cause);
    }
}
