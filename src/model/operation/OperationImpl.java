package model.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.contatti.Contatto;
import model.conto.Conto;
import model.douments.Data;
import model.douments.DataImpl;
import model.douments.Document;

public class OperationImpl implements Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445965986132678879L;

	private final Map<Conto, Double> map;
	private Integer operationNum;
	private final Data data;
	private Optional<String> description;
	private Optional<Document> document;

	public OperationImpl() {
		this.map = new HashMap<>();
		this.data = new DataImpl();
		this.description = Optional.empty();
		this.document = Optional.empty();
	}

	@Override
	public int getNum() {
		return this.operationNum;
	}

	@Override
	public void addContoMovimentato(final Conto c, final double importo) {
		if (importo == 0) {
			throw new IllegalArgumentException(
					"L'importo inserito è uguale a zero!");
		}
		if (this.map.containsKey(c)) {
			throw new IllegalArgumentException("Il conto " + c.getName()
					+ " era già stato movimentato!");
		}
		this.map.put(c, importo);
	}

	@Override
	public void setDescription(final String description) {
		this.description = Optional.ofNullable(description);
	}

	@Override
	public void setNumOperation(final int numOp) {
		if (this.operationNum == null) {
			this.operationNum = numOp;
		} else {
			throw new UnsupportedOperationException(
					"Il numero dell'operazione non si può settare due volte");
		}
	}

	@Override
	public Data getData() {
		return this.data;
	}

	@Override
	public boolean isBalanced() {
		return this.map.entrySet().stream().mapToDouble(e -> e.getValue())
				.sum() == 0;
	}

	@Override
	public Set<Conto> getContiMovimentati() {
		return this.map.keySet();
	}

	@Override
	public Map<Conto, Double> getContiMovimentatiEImporto() {
		return this.map.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	@Override
	public String getDescription() {
		return this.description.orElse("");
	}

	@Override
	public Optional<Document> generateDocument(Contatto... contatti) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canGenerateDocument() {
		// TODO Auto-generated method stub
		return false;
	}
}
