package exceptions;

public class IdAlreadyExists extends RuntimeException{
	public IdAlreadyExists(String msg){
        super(msg);
    }

    public IdAlreadyExists(String msg,Throwable e){
        super(msg,e);
    }
}
