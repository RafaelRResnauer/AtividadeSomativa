public class ByteCode {
    private String name;
    private int args;

    public ByteCode(String name, int args) {
        this.name = name;
        this.args = args;
    }

    public int getArgs() {
        return this.args;
    }

    public void setArgs(int args) {
        this.args = args;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
