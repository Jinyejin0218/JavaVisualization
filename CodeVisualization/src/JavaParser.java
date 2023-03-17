import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;


public class JavaParser {
   public CompilationUnit compilationUnit;

   public JavaParser() {
      String fileName = "./target/Test.java";
      File file = new File(fileName);

      if (!file.exists()) {
         System.out.println(file.getAbsolutePath() + "does not exist.");
         System.exit(1);
      }
      else {
         System.out.println(file.getAbsolutePath() + " load complete!");
         parse(file);
      }
   }

   public void parse(File file) {
      BufferedReader reader = null;

      try {
         reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

      StringBuilder source = new StringBuilder();
      char[] buf = new char[10];
      int numRead = 0;

      try {
         while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            source.append(readData);
            buf = new char[1024];
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      // 파일 읽기 끝 옵션 설정 시작
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(source.toString().toCharArray());
      parser.setStatementsRecovery(true);
      parser.setBindingsRecovery(true);
      parser.setResolveBindings(true);
      // 옵션끝
      Map<?, ?> options = JavaCore.getOptions();
      JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
      parser.setCompilerOptions(options);

      compilationUnit = (CompilationUnit) parser.createAST(null);
      startParser();
   }

   public void startParser() {
      List<AbstractTypeDeclaration> types = compilationUnit.types();
      for (AbstractTypeDeclaration type : types) {
         if (type instanceof TypeDeclaration) {
				typeDeclaration((TypeDeclaration) type);
         }
      }
   }
   
// TYPE
	public void typeDeclaration(TypeDeclaration type) {
		System.out.println("\n<< Class: " + type.getName().toString() +" >>");
		for (int i = 0; i < type.modifiers().size(); i++) {
			String typeModifier = type.modifiers().get(i).toString();
			if (typeModifier != null) {
				System.out.println("modifier: " + typeModifier);
			}
			boolean typeinterface = type.isInterface();
			System.out.println("interface: " + typeinterface);
		}
		for (Object bodyDeclaration : type.bodyDeclarations()) {
			if (bodyDeclaration instanceof FieldDeclaration) {
				System.out.println("\n=Field=");
				fieldDeclaration(bodyDeclaration);
			} else if (bodyDeclaration instanceof MethodDeclaration) {
				System.out.println("\n=Method=");
				methodDeclaration(bodyDeclaration);
			}
		}
		//***implements
	}

	// FIELD
	public void fieldDeclaration(Object bodyDeclaration) {
		FieldDeclaration field = (FieldDeclaration) bodyDeclaration;
		//field type
		String fieldType = field.getType().toString();
		System.out.println("type: " + fieldType);
		//field name
		try {
			if(field.fragments().get(0) instanceof VariableDeclarationFragment) {
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) field.fragments().get(0);
				String fieldName = vdf.getName().toString();
				System.out.println("name: "+fieldName);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	// METHOD
	public void methodDeclaration(Object bodyDeclaration) {
		MethodDeclaration method = (MethodDeclaration) bodyDeclaration;
		//Method modifier
		for (int i = 0; i < method.modifiers().size(); i++) {
			String methodModifier = method.modifiers().get(i).toString();
			System.out.print("modifier: ");
			if (methodModifier != null)
				System.out.println(methodModifier);
		}
		//Method ReturnType
		if(method.getReturnType2()!=null) {
			String returntype = method.getReturnType2().toString();
			System.out.println("returntype: " + returntype);
		}
		//Method Name
		String methodName = method.getName().toString();
		System.out.println("name: " + methodName);
		
		//***Method Parameter
		for(int i=0; i<method.parameters().size(); i++) {
			SingleVariableDeclaration p = (SingleVariableDeclaration) method.parameters().get(i);
			System.out.println("parameter type: " + p.getType());
			System.out.println("parameter name: "+p.getName());
		}
		
	}
}