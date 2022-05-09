package hansung.ac.kr.fooding.exception;

import hansung.ac.kr.fooding.var.CError;

public class C_IllegalStateException extends Exception{
    public C_IllegalStateException(CError cError){
        super(cError.getMessage());
    }

    public C_IllegalStateException(CError cError, String information){
        super(cError.getMessage() + information);
    }

    public C_IllegalStateException(CError cError, Throwable cause){
        super(cError.getMessage(), cause);
    }
}
