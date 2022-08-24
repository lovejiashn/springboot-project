package com.jiashn.springbootproject.jctree.aop;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Objects;
import java.util.Set;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/22 14:52
 **/
@SupportedAnnotationTypes("com.jiashn.springbootproject.jctree.aop.GenerateGetterAndSetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterAndSetterProcessor extends AbstractProcessor {
    private Messager messager;
    private JavacTrees javacTrees;

    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment)processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //拿到被注解标注的所有类
        Set<? extends Element> annotatedWith = roundEnv.getElementsAnnotatedWith(GenerateGetterAndSetter.class);
        annotatedWith.forEach(element -> {
            //得到类的抽象树结构
            JCTree tree = javacTrees.getTree(element);
            //遍历类、对象进行修改
            tree.accept(new TreeTranslator(){
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDecls = List.nil();
                    //在抽象树种找出所有的变量
                    for (JCTree jcTree : jcClassDecl.defs) {
                        if (Objects.equals(jcTree.getKind(), Tree.Kind.VARIABLE)){
                            jcVariableDecls = jcVariableDecls.append((JCTree.JCVariableDecl)jcTree);
                        }
                    }
                    // 对于变量进行生成方法的操作
                    for (JCTree.JCVariableDecl jcVariableDecl : jcVariableDecls) {
                        messager.printMessage(Diagnostic.Kind.NOTE,jcVariableDecl.getName() + "has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(generateSetterMethod(jcVariableDecl));
                        jcClassDecl.defs = jcClassDecl.defs.prepend(generateGetterMethod(jcVariableDecl));
                    }
                }
            });
        });
        return true;
    }

    private JCTree.JCMethodDecl generateSetterMethod(JCTree.JCVariableDecl jcVariableDecl){
        List<JCTree.JCVariableDecl> parameters = treeMaker.Params(List.nil(),jcVariableDecl.sym);
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        JCTree.JCReturn aReturn = treeMaker.Return(treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aReturn);
        JCTree.JCBlock block = treeMaker.Block(0L,statements.toList());
        //生成返回对象
        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName(),"set"), methodType, List.nil(), parameters, List.nil(), block, null);
    }
    
    private JCTree.JCMethodDecl generateGetterMethod(JCTree.JCVariableDecl jcVariableDecl){
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // 生成表达式
        JCTree.JCReturn aReturn = treeMaker.Return(treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aReturn);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        // 无入参
        // 生成返回对象
        JCTree.JCExpression returnType = treeMaker.Type(jcVariableDecl.getType().type);
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName(),"get"), returnType, List.nil(), List.nil(), List.nil(), block, null);
    }

    private Name getNewMethodName(Name name,String type){
        String s = name.toString();
        return names.fromString(type + s.substring(0,1).toUpperCase() + s.substring(1,name.length()));
    }
}