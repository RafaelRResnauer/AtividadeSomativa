import java.util.Vector;

/**
 * Máquina Virtual
 */
public class VirtualMachine {
    //memória
    private final Vector<Byte[]> stack; // pilha de operandos
    private final Vector<Byte[]> code; // armazenamento para o código
    private final Vector<Byte[]> globals; // escopo para variáveis globais
    private Context ctx; // escopo ativo
    private final Vector<FunctionMetaData> metaData; // Funções

    // Registradores globais detro da máquina
    private int ip; // ponteiro de instruções
    private int sp = -1; // ponteiro na pilha

    private final int DEFAULT_STACK_SIZE = 10000;
    private final int DEFAULT_CALL_STACK_SIZE = 10000;

    //coloque em true para o trace
    private final boolean TRACE = false;


    public VirtualMachine(Vector<Byte[]> code, int numGlobals, Vector<FunctionMetaData> metaData) {
        this.code = code;
        this.globals = new Vector<>();
        for(int i = 0; i < numGlobals; i++){ // Inicializa o globals
            globals.add(null);
        }
        this.stack = new Vector<>(DEFAULT_STACK_SIZE);
        this.metaData = metaData;
    }

    public void exec(int startIp) throws Exception {
        this.ip = startIp;
        this.ctx = new Context(null,0, metaData.get(0)); // Cria o contexto inicial
        simulaCPU();
    }
    private void simulaCPU() throws Exception {
        int opcode = Conversions.byteArrayToInt(code.get(ip));
        int a,b,addr,regNum;
        float c,d;

        while(opcode != OpCode.HALT.ordinal() && ip<code.size()){
            if(TRACE){
                System.out.print(traceInstruction() + "\t" + "\n");
            }
            ip++;

            switch(opcode){

                case 0: //IADD
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(a+b));
                    break;
                case 1: // ISUB
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(a-b));
                    break;
                case 2: // IMUL
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(a*b));
                    break;
                case 3: // ILT
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(a<b ? 1:0));
                    break;
                case 4: // IEQ
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes((a==b ? 1:0)));
                    break;
                case 5: // BR
                    ip = Conversions.byteArrayToInt(code.get(ip++));
                    break;
                case 6: // BRT
                    addr = Conversions.byteArrayToInt(code.get(ip++));
                    if(Conversions.byteArrayToInt(stack.get(sp))>=1){
                        ip = addr;
                    }
                    stack.remove(sp);
                    sp--;
                    break;
                case 7: // BRF
                    addr = Conversions.byteArrayToInt(code.get(ip++));
                    if(Conversions.byteArrayToInt(stack.get(sp))<1){
                        ip = addr;
                    }
                    stack.remove(sp);
                    sp--;
                    break;
                case 8: // ICONST
                    ++sp;
                    stack.add(code.get(ip++));
                    break;
                case 9: // LOAD
                    regNum = Conversions.byteArrayToInt(code.get(ip++));
                    ++sp;
                    stack.add(ctx.locals.get(regNum));
                    break;
                case 10: // GLOAD
                    addr = Conversions.byteArrayToInt(code.get(ip++));
                    ++sp;
                    stack.add(globals.get(addr));
                    break;
                case 11: // STORE
                    regNum = Conversions.byteArrayToInt(code.get(ip++));
                    ctx.locals.set(regNum,stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    break;
                case 12: // GSTORE
                    addr = Conversions.byteArrayToInt(code.get(ip++));
                    globals.set(addr,stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    break;
                case 13: // IPRINT
                    System.out.println(Conversions.byteArrayToInt(stack.get(sp)));
                    stack.remove(sp);
                    --sp;
                    break;
                case 14: // POP
                    stack.remove(sp);
                    --sp;
                    break;
                case 15: // CALL
                    // todos os argumentos devem estar na pilha
                    int funcIndex = Conversions.byteArrayToInt(code.get(ip++));
                    int numArgs = metaData.get(funcIndex).getNumArgs();

                    // determina um novo contexto que aponta para o contexto pai,
                    // contém o endereço de retorno e os metadados da função
                    ctx = new Context(ctx, ip, metaData.get(funcIndex));

                    // copia os args do stack no contexto novo
                    int firstArg = sp - numArgs + 1;
                    for(int i = 0; i < numArgs; i++) {
                        ctx.locals.set(i,stack.get(firstArg+i));

                    }
                    // remove os args da pilha
                    for(int i = sp; i>sp-numArgs; i--){
                        stack.remove(i);
                    }
                    sp -= numArgs; // remove argumentos do stack voltando o ponteiro
                    ip = metaData.get(funcIndex).getAddress(); // jump para a nova função.
                    break;
                case 16: // RET
                    ip = ctx.returnIp;
                    ctx = ctx.invokingContext;
                    break;
                case 17: // DUP
                    Byte[] top = stack.get(sp);
                    ++sp;
                    stack.add(top);
                    break;
                case 19: // FADD
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.floatToByteArray(c+d));
                    break;
                case 20: // FSUB
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.floatToByteArray(c-d));
                    break;
                case 21: // FMUL
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.floatToByteArray(c*d));
                    break;
                case 22: // FDIV
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.floatToByteArray(c/d));
                    break;
                case 23: // FLT
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(c < d ? 1 : 0));
                    break;
                case 24: // FEQ
                    c = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    d = Conversions.byteArrayToFloat(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    sp++;
                    stack.add(Conversions.intToBytes(c == d ? 1 : 0));
                    break;
                case 25: // FCONST
                    float f = Conversions.byteArrayToFloat(code.get(ip++));
                    ++sp;
                    stack.add(Conversions.floatToByteArray(f));
                    break;
                case 26: // FPRINT
                    System.out.println(Conversions.byteArrayToFloat(stack.get(sp)));
                    stack.remove(sp);
                    --sp;
                    break;
                case 27: //BITR
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    a = a >> b;
                    sp++;
                    stack.add(Conversions.intToBytes(a));
                    break;
                case 28: //BITL
                    a = Conversions.byteArrayToInt(stack.get(sp));
                    stack.remove(sp);
                    sp--;
                    b = Conversions.byteArrayToInt(stack.get(sp));
                    a = a << b;
                    sp++;
                    stack.add(Conversions.intToBytes(a));
                    break;
                default:
                    int temp = ip-1;
                    throw new Exception("OpCode Inválido: " + opcode +
                            " em: " + temp);
            }
            /*
            if (TRACE){
                System.out.println(traceStack() + "\t" + traceCallStack() + "\t");
            }
            */
            opcode = Conversions.byteArrayToInt(code.get(ip));
        }
        //imprime o trace na tela para permitir o acompanhamento da máquina
        if(TRACE){
            System.out.println(traceInstruction() + "\n");
        }
        /*
        if(TRACE){
            System.out.println("\t\t\t"+traceStack());
        }
        if(TRACE){
            System.out.println(traceDataMemory());
        }
        */
    }

    //apenas para os traces
    private String traceInstruction() {
        Bytecodes bytecodes = new Bytecodes();
        int opcode = Conversions.byteArrayToInt(code.get(ip)) + 1;
        String opName =  bytecodes.getByteCodes().get(opcode).getName();
        String ss = "";
        ss += "    " + ip + ":\t" + opName;
        int numArgs = bytecodes.getByteCodes().get(opcode).getArgs();
        if(opcode == OpCode.CALL.ordinal() + 1){
            ss += " " + metaData.get(Conversions.byteArrayToInt(code.get(ip+1))).getName();
        }else if(opcode > 18 && numArgs > 0){
            Vector<String> operands = new Vector<>();
            for(int i = ip+1; i <= ip+numArgs; i++){
                Float tmp = Conversions.byteArrayToFloat(code.get(i));
                operands.add(tmp.toString());
            }
            for(int i = 0; i < operands.size(); i++){
                String s = operands.get(i);
                ss += " ";
                if(i>0){
                    ss += ", ";
                }
                ss += s;
            }

        } else if(opcode <= 18 && numArgs > 0){
            Vector<String> operands = new Vector<>();
            for(int i = ip+1; i <= ip+numArgs; i++){
                Integer tmp = Conversions.byteArrayToInt(code.get(i));
                operands.add(tmp.toString());
            }
            for(int i = 0; i < operands.size(); i++){
                String s = operands.get(i);
                ss += " ";
                if(i>0){
                    ss += ", ";
                }
                ss += s;
            }
        }
        return ss;
    }

    //apenas para os traces
    private String traceStack() {
        String ss = "";
        ss += "stack=[";
        for(int i = 0; i <= sp; i++){
            int o = Conversions.byteArrayToInt(stack.get(i));
            ss += " ";
            ss += o;
        }
        ss += " ]";
        return ss;
    }

    //apenas para os traces
    private String traceCallStack() {
        Vector<String> stack = new Vector<>();
        Context c = ctx;
        while(c != null){
            if(c.metaData != null){
                stack.add(c.metaData.getName());
            }
            c = c.invokingContext;
        }
        String ss = "";
        ss += "Chamadas=(";
        for(String s : stack){
            ss += s + ",";
        }
        ss += ")";

        return ss;
    }

    private String traceDataMemory() {
        String ss = "";
        ss += "Dados em memoria: ";
        int addr = 0;
        for(Byte[] o : globals){
            ss += "    " + addr + ": " + o + "\n";
            addr++;
        }
        return ss;
    }
}
