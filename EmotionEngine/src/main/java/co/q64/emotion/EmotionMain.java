package co.q64.emotion;

import co.q64.emotion.annotation.Constants.Author;
import co.q64.emotion.annotation.Constants.Name;
import co.q64.emotion.annotation.Constants.Version;
import co.q64.emotion.annotation.GWT;
import co.q64.emotion.compiler.CompilerOutput;
import co.q64.emotion.inject.StandardModule;
import co.q64.emotion.inject.SystemModule;
import co.q64.emotion.lang.opcode.Opcodes;
import co.q64.emotion.lexer.Lexer;
import co.q64.emotion.runtime.Output;
import co.q64.emotion.runtime.mock.MockOutput;
import co.q64.emotion.util.ArgumentIterator;
import com.google.common.annotations.GwtIncompatible;
import dagger.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Singleton
@GwtIncompatible(GWT.MESSAGE)
public class EmotionMain {
	protected @Inject EmotionEngine emotion;
	protected @Inject Output output;
	protected @Inject Lexer lexer;
	protected @Inject Opcodes opcodes;
	protected @Inject MockOutput mockOutput;
	protected @Inject co.q64.emotion.util.ArgumentIteratorFactory arguments;
	protected @Inject @Version String version;
	protected @Inject @Name String name;
	protected @Inject @Author String author;

	private String[] cmdArgs;

	private EmotionMain(String[] args) {
		this.cmdArgs = args;
	}

	public static void main(String[] args) {
		EmotionMainComponent component = DaggerEmotionMain_EmotionMainComponent.create();
		component.inject(new EmotionMain(args));
	}

	@Inject
	protected void start() {
		ArgumentIterator args = arguments.create(cmdArgs);
		if (!args.hasNext()) {
			output.println(name + " version " + version + " by " + author);
			return;
		}
		String current = args.next();
		try {
			if (current.equalsIgnoreCase("dev")) {
				if (!args.hasNext()) {
					output.println("Specify a script file name!");
					return;
				}
				CompilerOutput compiled = emotion.compileProgram(Files.readAllLines(new File(args.next()).toPath()));
				for (String s : compiled.getDisplayOutput()) {
					output.println(s);
				}
				if (compiled.isSuccess()) {
					output.println("");
					emotion.runProgram(compiled.getProgram(), getArgs(args), output);
				}
			} /*else if (current.equalsIgnoreCase("debug")) {
				if (!args.hasNext()) {
					output.println("Specify a script file name!");
					return;
				}
				CompilerOutput compiled = emotion.compileProgram(Files.readAllLines(new File(args.next()).toPath()));
				for (String s : compiled.getDisplayOutput()) {
					output.println(s);
				}
				if (!compiled.isSuccess()) {
					return;
				}
				try (Scanner in = new Scanner(System.in)) {
					output.println("");
					output.println("Press any key to begin execution...");
					in.nextLine();
					List<Instruction> insns = lexer.parse(compiled.getProgram(), mockOutput);
					List<String> outputBuffer = new ArrayList<String>();
					Program program = programFactory.create(compiled.getProgram(), getArgs(args), new Output() {
				
						@Override
						public void println(String message) {
							outputBuffer.add(message);
						}
				
						@Override
						public void print(String message) {
							if (outputBuffer.size() == 0) {
								println(message);
								return;
							}
							outputBuffer.set(outputBuffer.size() - 1, outputBuffer.get(outputBuffer.size() - 1) + message);
						}
					});
					program.execute(false);
					while (true) {
						boolean terminated = program.isTerminated() || program.getInstruction() >= insns.size();
						if (terminated) {
							if (program.isPrintOnTerminate()) {
								outputBuffer.add(program.getStack().pop().toString());
							}
						}
						for (int i = 0; i < 50; i++) {
							output.println("");
						}
						output.println("Program");
						for (int i = 0; i < insns.size(); i++) {
							output.print(i == program.getInstruction() ? " => " : " == ");
							output.println(insns.get(i).getInstruction());
						}
						output.println("");
						output.println("Stack");
						for (Value value : program.getStack().getStack()) {
							output.println(" " + value.toString());
						}
						output.println("");
						output.println("Output");
						for (String s : outputBuffer) {
							output.println(s);
						}
						output.println("");
						output.println("Press any key to continue execution...");
						in.nextLine();
						if (terminated) {
							break;
						}
						program.step();
					}
				}
				}*/ else if (current.equalsIgnoreCase("opcodes")) {
				output.println(opcodes.getDebugInfo());
			} else {
				CompilerOutput co = emotion.compileProgram(Files.readAllLines(new File(current).toPath()));
				for (String s : co.getDisplayOutput()) {
					System.out.println(s);
				}
				if (co.isSuccess()) {
					String prog = co.getProgram();
					System.out.println("Running...");
					emotion.runProgram(prog, getArgs(args), output);
				}
			}
		} catch (FileNotFoundException e) {
			output.println("The file '" + e.getMessage() + "' could not be found!");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			output.println("Failed to read script file!");
			return;
		}
	}

	private String getArgs(ArgumentIterator itr) {
		StringBuilder result = new StringBuilder();
		while (itr.hasNext()) {
			result.append(itr.next());
			if (itr.hasNext()) {
				result.append(" ");
			}
		}
		return result.toString();
	}

	@Singleton
	@GwtIncompatible(GWT.MESSAGE)
	@Component(modules = { SystemModule.class, StandardModule.class })
	public static interface EmotionMainComponent {
		public void inject(EmotionMain main);

		public EmotionEngine getJstx();
	}
}
