/**
 * Classe para salver informações sobre uma função
 */

public class FunctionMetaData {
    private String name; // Nome da função
    private int numArgs; // Número de argumentos
    private int numLocals; // Número de var locais
    private int address; // Endereço na memória

    public FunctionMetaData(String name, int numArgs, int numLocals, int address) {
        this.name = name;
        this.numArgs = numArgs;
        this.numLocals = numLocals;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumArgs() {
        return numArgs;
    }

    public void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
    }

    public int getNumLocals() {
        return numLocals;
    }

    public void setNumLocals(int numLocals) {
        this.numLocals = numLocals;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
