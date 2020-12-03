package jpabook.jpashop.exception;

public class NotEnoughStockExcetion extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NotEnoughStockExcetion() {
        super();
    }

    public NotEnoughStockExcetion(String message) {
        super(message);
    }

    public NotEnoughStockExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExcetion(Throwable cause) {
        super(cause);
    }

}
