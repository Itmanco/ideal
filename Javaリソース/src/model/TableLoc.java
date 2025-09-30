package model;

import java.io.Serializable;

/**
 * テーブルの場所情報を管理するJavaBeansクラスです。
 * このクラスは、テーブルのID、名前、および最大収容人数を保持します。
 *
 * This is a JavaBeans class for managing table location information.
 * This class holds the table's ID, name, and maximum capacity.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class TableLoc implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * テーブルID。
	 * Table ID.
	 */
	private int tableId;
	/**
	 * テーブル名。
	 * Table name.
	 */
	private String tableName;
	/**
	 * 最大収容人数。
	 * Maximum capacity.
	 */
	private int maxCapacity;

	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public TableLoc() {
		super();
	}
	
	

	@Override
	public String toString() {
		return "TableLoc [tableId=" + tableId + ", tableName=" + tableName + ", maxCapacity=" + maxCapacity + "]";
	}



	/**
	 * テーブルIDを取得します。
	 * Retrieves the table ID.
	 *
	 * @return テーブルID / Table ID
	 */
	public int getTableId() {
		return tableId;
	}

	/**
	 * テーブルIDを設定します。
	 * Sets the table ID.
	 *
	 * @param tableId 設定するテーブルID / Table ID to set
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	/**
	 * テーブル名を取得します。
	 * Retrieves the table name.
	 *
	 * @return テーブル名 / Table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * テーブル名を設定します。
	 * Sets the table name.
	 *
	 * @param tableName 設定するテーブル名 / Table name to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 最大収容人数を取得します。
	 * Retrieves the maximum capacity.
	 *
	 * @return 最大収容人数 / Maximum capacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * 最大収容人数を設定します。
	 * Sets the maximum capacity.
	 *
	 * @param maxCapacity 設定する最大収容人数 / Maximum capacity to set
	 */
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
}