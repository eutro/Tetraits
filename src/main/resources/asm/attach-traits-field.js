function initializeCoreMod() {
    var ASM = Java.type("net.minecraftforge.coremod.api.ASMAPI");
    var Opcodes = Java.type("org.objectweb.asm.Opcodes");
    var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
    var FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
    var FieldNode = Java.type("org.objectweb.asm.tree.FieldNode");
    var InsnList = Java.type("org.objectweb.asm.tree.InsnList");

    var FIELD_NAME = "tetraits";
    
    return {
        "add-field": {
            "target": {
                "type": "CLASS",
                "name": "se.mickelus.tetra.module.data.ModuleVariantData"
            },
            "transformer": function (clazz) {
                clazz.fields.add(new FieldNode(
                    Opcodes.ACC_PUBLIC,
                    FIELD_NAME,
                    "Ljava/util/Map;",
                    "Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;",
                    null
                ));

                return clazz;
            }
        },
        "assemble-getter": {
            "target": {
                "type": "METHOD",
                "class": "eutros.tetraits.handler.ASMFieldHandler",
                "methodName": "getTetraitsField",
                "methodDesc": "(Lse/mickelus/tetra/module/data/ModuleVariantData;)Ljava/util/Map;"
            },
            "transformer": function (method) {
                var newInstructions = new InsnList();

                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                newInstructions.add(new FieldInsnNode(
                    Opcodes.GETFIELD,
                    "se/mickelus/tetra/module/data/ModuleVariantData",
                    FIELD_NAME,
                    "Ljava/util/Map;"
                ));

                var target = ASM.findFirstInstruction(method, Opcodes.INVOKESTATIC);

                method.instructions.insert(target, newInstructions);
                method.instructions.remove(target);

                return method;
            }
        }
    }
}
