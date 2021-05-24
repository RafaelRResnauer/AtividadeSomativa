import java.util.Vector;

/**
 * Classe para acessar os bytecodes
 */

public class Bytecodes {
    private Vector<ByteCode> byteCodes = new Vector<ByteCode>();

    public Bytecodes(){
        byteCodes.add(new ByteCode("nop", 0));
        byteCodes.add(new ByteCode("iadd", 0)); // int soma
        byteCodes.add(new ByteCode("isub", 0)); // int subtração
        byteCodes.add(new ByteCode("imul", 0)); // int multiplicação
        byteCodes.add(new ByteCode("ilt", 0)); // int menor que
        byteCodes.add(new ByteCode("ieq", 0)); // int igual a
        byteCodes.add(new ByteCode("br", 1)); // branch
        byteCodes.add(new ByteCode("brt", 1)); // branch se verdadeiro
        byteCodes.add(new ByteCode("brf", 1)); // branch se falso
        byteCodes.add(new ByteCode("iconst", 1)); // push constante inteiro
        byteCodes.add(new ByteCode("load", 1)); // load de um contexto local
        byteCodes.add(new ByteCode("gload", 1)); // load de memória local
        byteCodes.add(new ByteCode("store", 1)); // store no contexto local
        byteCodes.add(new ByteCode("gstore", 1)); // store na memória global
        byteCodes.add(new ByteCode("print", 0)); // print topo do stack
        byteCodes.add(new ByteCode("pop", 0)); // Retira do stack
        byteCodes.add(new ByteCode("call", 1)); // call uma função
        byteCodes.add(new ByteCode("ret", 0)); // return com, ou sem, valor.
        byteCodes.add(new ByteCode("dup", 0)); // apaga a pilha (volta para o topo)
        byteCodes.add(new ByteCode("halt", 0)); // parar
        byteCodes.add(new ByteCode("fadd", 0)); // float soma
        byteCodes.add(new ByteCode("fsub", 0)); // float subtração
        byteCodes.add(new ByteCode("fmul", 0)); // float multiplicação
        byteCodes.add(new ByteCode("fdiv", 0)); // float divisão
        byteCodes.add(new ByteCode("flt", 0)); // float menor que
        byteCodes.add(new ByteCode("feq", 0)); // float igual a
        byteCodes.add(new ByteCode("fconst", 1)); // push constante float
        byteCodes.add(new ByteCode("fprint", 0)); // print topo do stack
        byteCodes.add(new ByteCode("bitr",0));
        byteCodes.add(new ByteCode("bitl",0));
    }

    public Vector<ByteCode> getByteCodes() {
        return byteCodes;
    }

    public void setByteCodes(Vector<ByteCode> byteCodes) {
        this.byteCodes = byteCodes;
    }
}
