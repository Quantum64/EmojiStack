package co.q64.emotion.ast;

import javax.inject.Inject;

import com.google.auto.factory.AutoFactory;

import co.q64.emotion.lang.Instruction;
import co.q64.emotion.lang.Program;

@AutoFactory
public class ASTInstruction implements ASTNode {
	private Program program;
	private Instruction insn;

	protected @Inject ASTInstruction(Program program, Instruction insn) {
		this.program = program;
		this.insn = insn;
	}

	@Override
	public void enter() {
		try {
			insn.execute(program.getStack());
		} catch (Exception e) {
			program.crash(e.getClass().getSimpleName() + ": " + e.getMessage() + " [Instruction: " + insn.getInstruction() + "]");
		}
	}
}