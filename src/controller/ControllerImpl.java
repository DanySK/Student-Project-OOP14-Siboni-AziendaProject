package controller;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import view.View;
import model.Model;
import model.contatti.Contatto;

public class ControllerImpl implements Controller {

	private final Model m;
	private final Set<View> views = new HashSet<>();
	private final String savePath;

	public ControllerImpl(final Model m, final String path) {
		this.m = m;
		m.setController(this);
		this.savePath = path;
	}

	@Override
	public void load() {
		try {
			this.m.load(savePath);
		} catch (FileNotFoundException e) {
			// TODO
			// Generare un messaggio d'errore nella vista!
		}
	}

	@Override
	public void save()  {
		try {
			this.m.save(savePath);
		} catch (FileNotFoundException e) {
			// TODO
			// Generare un messaggio d'errore nella vista!
		}
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
	public void attachView(final View v) {
		this.views.add(v);
	}

	@Override
	public void removeView(final View v) {
		this.views.remove(v);
	}

}
