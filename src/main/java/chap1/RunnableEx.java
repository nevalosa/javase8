package chap1;

@FunctionalInterface
public interface RunnableEx {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     */
    void run() throws Exception;
    
    public static Runnable uncheck(RunnableEx ex){
    	return () -> {try {
			ex.run();
		} catch (Exception e) {
			e.printStackTrace();
		}};
    }
    
    public static Runnable andThen(final Runnable t1, final Runnable t2){
    	
    	return ()->{t1.run(); t2.run();};
    }
}
