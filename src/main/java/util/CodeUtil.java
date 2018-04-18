package util;

import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;

public class CodeUtil {

    public CodeUtil() {
    }

    public static void main(String args[]) {
        String tableName = "Customer";
        String codeName = "用户表";
        CodeGenerateFactory.codeGenerateByFTL(tableName, codeName, FtlDef.KEY_TYPE_02);
    }
}
