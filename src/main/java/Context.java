import java.util.Vector;

/**
 * Classe para salvar o contexto
 */
public class Context {
    Context invokingContext; // contexto que chamou a função - parent
    FunctionMetaData metaData; // dados da função a ser executada
    int returnIp; // ponteiro de intrução para retorno de função
    Vector<Byte[]> locals; // args + locals, indexed from 0

    public Context(Context invokingContext, int returnIp , FunctionMetaData metaData) {
        this.invokingContext = invokingContext;
        this.metaData = metaData;
        this.returnIp = returnIp;
        this.locals = new Vector<>();
        for(int i = 0; i<metaData.getNumArgs() + metaData.getNumLocals(); i++){ // inicializa o locals para ser utilizado depois
            locals.add(null);
        }
    }
}
