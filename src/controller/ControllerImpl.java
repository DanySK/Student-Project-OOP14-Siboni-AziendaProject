package controller;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import view.View;
import model.Model;
import model.contatti.Contatto;
import model.conto.Conto;
import model.operation.Operation;
import model.operation.OperationImpl;

public class ControllerImpl implements Controller {

	private Model model;
	private View view;
	private final String savePath;

	public ControllerImpl(final String path) {
		this.savePath = path;
	}

	@Override
	public void load() {
		this.model.load(savePath);
	}

	@Override
	public void save() {
		this.model.save(savePath);
	}

	@Override
	public boolean aggiuntaOperazione(final List<Conto> conti,
			final List<Double> importi) {
		
		if (conti.size() == importi.size()) {
			final Operation o = new OperationImpl();
			for (int i = 0; i < conti.size(); i++) {
				o.setContoMovimentato(conti.get(i), importi.get(i));
			}
			if(o.isBalanced()){
				o.applicaMovimenti();
				this.model.addOperation(o);
				return true;
			}else{
				this.view.displayError("L'operazione non è bilanciata!!\nControllare gli importi...");
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public Set<Conto> getInsiemeConti() {
		return this.model.getConti();
	}

	@Override
	public Set<Contatto> getInsiemeContatti() {
		return this.model.getContatti();
	}

	@Override
	public Contatto askOurContact() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contatto wichContattoToMantain(final Set<Contatto> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setView(final View v) {
		this.view = v;
	}

	@Override
	public void setModel(final Model m) {
		this.model = m;
		
	}
	
	@Override
	public void showMenu(final String appName) {
		this.view.displayMainMenu(appName);
	}

	@Override
	public void showErrorMessage(final String errorMessage) {
		this.view.displayError(errorMessage);
	}

	@Override
	public void actionPerformed(final ActionEvent evento) {

	}

}
