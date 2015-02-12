package model.data;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Implementazione concreta della classe Data.
 * 
 * @author Enrico
 *
 */
public class DataImpl implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1347212958680819868L;
	
	private static final String DATA_REGEX = "[0-9]?[0-9]/[0-9]?[0-9]/[0-9][0-9][0-9][0-9]";
	private static final int LOWEST_DATE = 1900;
	private static final int MONTHS_IN_A_YEAR = 12;
	private static final int DAYS_IN_SHORT_MONTHS = 30;
	private static final int DAYS_IN_LONG_MONTHS = 31;
	private static final int FEBRUARY_DAYS = 28;
	private static final int FEBRUARY_DAYS_BISSEXTILE_YEAR = 29;
	private static final List<Integer> MONTHS_WITH_30_DAYS = Arrays.asList(4,
			6, 9, 11);

	private final int giorno;
	private final int mese;
	private final int anno;

	/**
	 * Crea un nuovo oggetto data che rappresenta la data corrente.
	 */
	public DataImpl() {
		final Calendar c = Calendar.getInstance();

		this.anno = c.get(Calendar.YEAR);
		this.mese = c.get(Calendar.MONTH) + 1;
		this.giorno = c.get(Calendar.DATE);
	}

	/**
	 * Crea un nuovo oggetto data a partire dai parametri passati.
	 * 
	 * @param giorno
	 *            numero del giorno
	 * @param mese
	 *            numero del mese
	 * @param anno
	 *            numero dell'anno
	 * 
	 * @throws IllegalArgumentException
	 *             se i parametri passati sono incongruenti ad una data
	 */
	public DataImpl(final int giorno, final int mese, final int anno) {

		controlInput(giorno, mese, anno);

		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
	}

	/**
	 * Crea un nuovo oggetto data a partire dalla stringa passata, la quale deve
	 * essere nel formato GG/MM/AAAA.
	 * 
	 * @param data
	 * 
	 * @throws IllegalArgumentException
	 *             se la stringa passata non corrisponde al formato previsto, o
	 *             se la data è incongruenete
	 */
	public DataImpl(final String data) {
		if (!data.matches(DATA_REGEX)) {
			throw new IllegalArgumentException(
					"La data deve essere nel formato GG/MM/AAAA");
		}
		final String[] dataParts = data.split("/");

		final int day = Integer.parseInt(dataParts[0]);
		final int month = Integer.parseInt(dataParts[1]);
		final int year = Integer.parseInt(dataParts[2]);

		controlInput(day, month, year);

		this.giorno = day;
		this.mese = month;
		this.anno = year;
	}

	@Override
	public int compareTo(final Data otherData) {
		return this.anno == otherData.getAnno() ? this.mese == otherData
				.getMese() ? this.giorno == otherData.getGiorno() ? 0
				: this.giorno - otherData.getGiorno() : this.mese
				- otherData.getMese() : this.anno - otherData.getAnno();
	}

	@Override
	public int getGiorno() {
		return this.giorno;
	}

	@Override
	public int getMese() {
		return this.mese;
	}

	@Override
	public int getAnno() {
		return this.anno;
	}

	/**
	 * Metodo che controlla l'input per la costruzione di una Data
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * 
	 * @throws IllegalArgumentException
	 *             se ci sono incongruenze
	 */
	private void controlInput(final int day, final int month, final int year) {
		if (year < LOWEST_DATE) {
			throw new IllegalArgumentException(
					"L'anno no può essere inferiore al " + LOWEST_DATE);
		}

		if (month < 1 || month > MONTHS_IN_A_YEAR) {
			throw new IllegalArgumentException(
					"Il mese deve essere un numero compreso tra 1 e "
							+ MONTHS_IN_A_YEAR);
		}

		if (day < 1) {
			throw new IllegalArgumentException(
					"Il giorno non puo' essere negativo o 0");
		} else if (month == 2) {
			if (year % 4 == 0) {
				if (day > FEBRUARY_DAYS_BISSEXTILE_YEAR) {
					throw new IllegalArgumentException(
							"Il giorno non puo' essere maggiore di "
									+ FEBRUARY_DAYS_BISSEXTILE_YEAR);
				}
			} else {
				if (day > FEBRUARY_DAYS) {
					throw new IllegalArgumentException(
							"Il giorno non puo' essere maggiore di "
									+ FEBRUARY_DAYS);
				}
			}

		} else if (MONTHS_WITH_30_DAYS.contains(month)
				&& day > DAYS_IN_SHORT_MONTHS) {
			throw new IllegalArgumentException(
					"Il giorno non puo' essere maggiore di "
							+ DAYS_IN_SHORT_MONTHS);

		} else if (day > DAYS_IN_LONG_MONTHS) {
			throw new IllegalArgumentException(
					"Il giorno non puo' essere maggiore di "
							+ DAYS_IN_LONG_MONTHS);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		result = prime * result + giorno;
		result = prime * result + mese;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataImpl other = (DataImpl) obj;
		if (anno != other.anno)
			return false;
		if (giorno != other.giorno)
			return false;
		if (mese != other.mese)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.giorno + "/" + this.mese + "/" + this.anno;
	}

}
