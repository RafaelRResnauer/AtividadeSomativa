import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {

    static void addFunction(){
        Vector<Byte[]> code = new Vector<>();
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(0)); // move argumentos do regitrador 0 para o stack
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(1)); // move argument do regitrador 1 para o stack
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(2));
        code.add(Conversions.intToBytes(OpCode.IADD.ordinal()));
        code.add(Conversions.intToBytes(OpCode.IADD.ordinal()));
        code.add(Conversions.intToBytes(OpCode.RET.ordinal()));
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(16)); //Números inteiros que serão somados.
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(15));
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(21));
        code.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code.add(Conversions.intToBytes(1)); // move argumentos do stack para os registradores
        code.add(Conversions.intToBytes(OpCode.PRINT.ordinal()));
        code.add(Conversions.intToBytes(OpCode.HALT.ordinal()));

        Vector<FunctionMetaData> fmd = new Vector<>();
        fmd.add(new FunctionMetaData("main",0,0,9));
        fmd.add(new FunctionMetaData("addFunction",3,3,0));
        VirtualMachine vm = new VirtualMachine(code,0,fmd);
        try {
            vm.exec(9);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void factorial(){
        Vector<Byte[]> code = new Vector<>();
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(7));
        code.add(Conversions.intToBytes(OpCode.STORE.ordinal())); code.add(Conversions.intToBytes(0));

        Vector<FunctionMetaData> fmd = new Vector<>();
        fmd.add(new FunctionMetaData("main",0,1,0));

        Vector<Byte[]> code2 = new Vector<>();
        //.def factorial: ARGS=1, LOCALS=0	ADDRESS
        //	IF N < 2 RETURN 1
        code2.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code2.add(Conversions.intToBytes(0));
        code2.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code2.add(Conversions.intToBytes(2));
        code2.add(Conversions.intToBytes(OpCode.ILT.ordinal()));
        code2.add(Conversions.intToBytes(OpCode.BRF.ordinal())); code2.add(Conversions.intToBytes(10));
        code2.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code2.add(Conversions.intToBytes(1));
        code2.add(Conversions.intToBytes(OpCode.RET.ordinal()));

        code2.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code2.add(Conversions.intToBytes(0));
        code2.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code2.add(Conversions.intToBytes(0));
        code2.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code2.add(Conversions.intToBytes(1));
        code2.add(Conversions.intToBytes(OpCode.ISUB.ordinal()));
        code2.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code2.add(Conversions.intToBytes(1));
        code2.add(Conversions.intToBytes(OpCode.IMUL.ordinal()));
        code2.add(Conversions.intToBytes(OpCode.RET.ordinal()));

        code2.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code2.add(Conversions.intToBytes(6));
        code2.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code2.add(Conversions.intToBytes(1));
        code2.add(Conversions.intToBytes(OpCode.PRINT.ordinal()));
        code2.add(Conversions.intToBytes(OpCode.HALT.ordinal()));

        Vector<FunctionMetaData> fmd2 = new Vector<>();
        fmd2.add(new FunctionMetaData("main",0,0,21));
        fmd2.add(new FunctionMetaData("factorial",1,0,0));

        VirtualMachine vm = new VirtualMachine(code2,0,fmd2);

        try {
            vm.exec(21);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void fib(){
        Vector<Byte[]> code = new Vector<>();
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(0)); // move argumentos do regitrador 0 para o stack
        code.add(Conversions.intToBytes(OpCode.DUP.ordinal())); // move argument do regitrador 1 para o stack
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(2));
        code.add(Conversions.intToBytes(OpCode.ILT.ordinal()));
        code.add(Conversions.intToBytes(OpCode.BRF.ordinal())); code.add(Conversions.intToBytes(9));
        code.add(Conversions.intToBytes(OpCode.RET.ordinal()));

        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(1)); //Números inteiros que serão somados.
        code.add(Conversions.intToBytes(OpCode.ISUB.ordinal()));
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(0));
        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(2));
        code.add(Conversions.intToBytes(OpCode.ISUB.ordinal()));
        code.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code.add(Conversions.intToBytes(1)); // move argumentos do stack para os registradores
        code.add(Conversions.intToBytes(OpCode.STORE.ordinal())); code.add(Conversions.intToBytes(0));
        code.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code.add(Conversions.intToBytes(1)); // move argumentos do stack para os registradores
        code.add(Conversions.intToBytes(OpCode.LOAD.ordinal())); code.add(Conversions.intToBytes(0));
        code.add(Conversions.intToBytes(OpCode.IADD.ordinal()));
        code.add(Conversions.intToBytes(OpCode.RET.ordinal()));

        code.add(Conversions.intToBytes(OpCode.ICONST.ordinal())); code.add(Conversions.intToBytes(20));
        code.add(Conversions.intToBytes(OpCode.CALL.ordinal())); code.add(Conversions.intToBytes(1));
        code.add(Conversions.intToBytes(OpCode.PRINT.ordinal()));
        code.add(Conversions.intToBytes(OpCode.HALT.ordinal()));

        final int MAIN_FUNCTION = 27;

        Vector<FunctionMetaData> fmd = new Vector<>();
        fmd.add(new FunctionMetaData("main",0,0,MAIN_FUNCTION));
        fmd.add(new FunctionMetaData("fibonacci",1,1,0));

        VirtualMachine vm = new VirtualMachine(code,0,fmd);

        try {
            vm.exec(MAIN_FUNCTION);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        //soma
        addFunction();
        //fatorial
        //factorial();
        //fibonacci
        //fib();

    }
}
