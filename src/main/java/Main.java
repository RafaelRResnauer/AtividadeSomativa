import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {

    static void addFunction(){
        Vector<Integer> code = new Vector<>();
        code.add(OpCode.LOAD.ordinal()); code.add(0); // move argumentos do regitrador 0 para o stack
        code.add(OpCode.LOAD.ordinal()); code.add(1); // move argument do regitrador 1 para o stack
        code.add(OpCode.LOAD.ordinal()); code.add(2);
        code.add(OpCode.IADD.ordinal());
        code.add(OpCode.IADD.ordinal());
        code.add(OpCode.RET.ordinal());
        code.add(OpCode.ICONST.ordinal()); code.add(16); //Números inteiros que serão somados.
        code.add(OpCode.ICONST.ordinal()); code.add(15);
        code.add(OpCode.ICONST.ordinal()); code.add(21);
        code.add(OpCode.CALL.ordinal()); code.add(1); // move argumentos do stack para os registradores
        code.add(OpCode.PRINT.ordinal());
        code.add(OpCode.HALT.ordinal());

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
        Vector<Integer> code = new Vector<>();
        code.add(OpCode.ICONST.ordinal()); code.add(7);
        code.add(OpCode.STORE.ordinal()); code.add(0);

        Vector<FunctionMetaData> fmd = new Vector<>();
        fmd.add(new FunctionMetaData("main",0,1,0));

        Vector<Integer> code2 = new Vector<>();
        //.def factorial: ARGS=1, LOCALS=0	ADDRESS
        //	IF N < 2 RETURN 1
        code2.add(OpCode.LOAD.ordinal()); code2.add(0);
        code2.add(OpCode.ICONST.ordinal()); code2.add(2);
        code2.add(OpCode.ILT.ordinal());
        code2.add(OpCode.BRF.ordinal()); code2.add(10);
        code2.add(OpCode.ICONST.ordinal()); code2.add(1);
        code2.add(OpCode.RET.ordinal());

        code2.add(OpCode.LOAD.ordinal()); code2.add(0);
        code2.add(OpCode.LOAD.ordinal()); code2.add(0);
        code2.add(OpCode.ICONST.ordinal()); code2.add(1);
        code2.add(OpCode.ISUB.ordinal());
        code2.add(OpCode.CALL.ordinal()); code2.add(1);
        code2.add(OpCode.IMUL.ordinal());
        code2.add(OpCode.RET.ordinal());

        code2.add(OpCode.ICONST.ordinal()); code2.add(6);
        code2.add(OpCode.CALL.ordinal()); code2.add(1);
        code2.add(OpCode.PRINT.ordinal());
        code2.add(OpCode.HALT.ordinal());

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
        Vector<Integer> code = new Vector<>();
        code.add(OpCode.LOAD.ordinal()); code.add(0); // move argumentos do regitrador 0 para o stack
        code.add(OpCode.DUP.ordinal()); // move argument do regitrador 1 para o stack
        code.add(OpCode.ICONST.ordinal()); code.add(2);
        code.add(OpCode.ILT.ordinal());
        code.add(OpCode.BRF.ordinal()); code.add(9);
        code.add(OpCode.RET.ordinal());

        code.add(OpCode.ICONST.ordinal()); code.add(1); //Números inteiros que serão somados.
        code.add(OpCode.ISUB.ordinal());
        code.add(OpCode.LOAD.ordinal()); code.add(0);
        code.add(OpCode.ICONST.ordinal()); code.add(2);
        code.add(OpCode.ISUB.ordinal());
        code.add(OpCode.CALL.ordinal()); code.add(1); // move argumentos do stack para os registradores
        code.add(OpCode.STORE.ordinal()); code.add(0);
        code.add(OpCode.CALL.ordinal()); code.add(1); // move argumentos do stack para os registradores
        code.add(OpCode.LOAD.ordinal()); code.add(0);
        code.add(OpCode.IADD.ordinal());
        code.add(OpCode.RET.ordinal());

        code.add(OpCode.ICONST.ordinal()); code.add(20);
        code.add(OpCode.CALL.ordinal()); code.add(1);
        code.add(OpCode.PRINT.ordinal());
        code.add(OpCode.HALT.ordinal());

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
        factorial();
        //fibonacci
        fib();

    }
}
