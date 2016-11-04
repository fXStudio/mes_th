package helper.excel.persistence;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class AbstractPersistence {
	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	/**
	 * –Ú¡–∫≈
	 * 
	 * @param seq
	 * @return
	 */
	protected final String getSeq(String seq) {
		StringBuilder sb = new StringBuilder();
		sb.append(getPrefix());

		for (int i = seq.length(); i < 4; i++) {
			sb.append("0");
		}
		sb.append(seq);

		return sb.toString();
	}

	/**
	 * @param date
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	protected final Timestamp toDateTime(Date date, Date time) throws ParseException {
		return new Timestamp(df.parse(
		        new SimpleDateFormat("yyyy/MM/dd").format(date) + " " + new SimpleDateFormat("HH:mm").format(time))
		        .getTime());
	}

	/**
	 * @return ±‰∏¸”Ôæ‰
	 */
	protected String toSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Update cardata ");
		sb.append(" set cvincode = ?, dabegin = ?, cseqno_a = ? ");
		sb.append(" where substring(ccarno, 3, 8) = ? ");

		return sb.toString();
	}

	/**
	 * @return
	 */
	protected abstract String getPrefix();
}
