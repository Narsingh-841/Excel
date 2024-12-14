package FixedAsset.Excel;

class XLSXRecord {
	 private String assetNumber;                      // Updated from "code"
	    private String assetName;                        // Updated from "name"
	    private String purchaseDate;                     // New field
	    private String purchasePrice;                    // New field
	    private String assetType;                        // Updated from "type"
	    private String bookRate;                         // New field
	    private String closingBookRate;                  // New field
	    private String bookAccumulatedDepreciation;      // New field
	    private String bookDepMethodValue;              // New field
	    private String taxRate;
	    private String taxDepMethodValue;   
	    public String getTaxAccumulatedDepreciation() {
			return TaxAccumulatedDepreciation;
		}

		public void setTaxAccumulatedDepreciation(String taxAccumulatedDepreciation) {
			TaxAccumulatedDepreciation = taxAccumulatedDepreciation;
		}

		private String TaxAccumulatedDepreciation;
	    public String getTaxDepMethodValue() {
			return taxDepMethodValue;
		}

		public void setTaxDepMethodValue(String taxDepMethodValue) {
			this.taxDepMethodValue = taxDepMethodValue;
		}

		public String getTaxRate() {
			return taxRate;
		}

		public void setTaxRate(String taxRate) {
			this.taxRate = taxRate;
		}

		// Getters and Setters
	    public String getAssetNumber() {
	        return assetNumber;
	    }

	    public void setAssetNumber(String assetNumber) {
	        this.assetNumber = assetNumber;
	    }

	    public String getAssetName() {
	        return assetName;
	    }

	    public void setAssetName(String assetName) {
	        this.assetName = assetName;
	    }

	    public String getPurchaseDate() {
	        return purchaseDate;
	    }

	    public void setPurchaseDate(String purchaseDate) {
	        this.purchaseDate = purchaseDate;
	    }

	    public String getPurchasePrice() {
	        return purchasePrice;
	    }

	    public void setPurchasePrice(String purchasePrice) {
	        this.purchasePrice = purchasePrice;
	    }

	    public String getAssetType() {
	        return assetType;
	    }

	    public void setAssetType(String assetType) {
	        this.assetType = assetType;
	    }

	    public String getBookRate() {
	        return bookRate;
	    }

	    public void setBookRate(String bookRate) {
	        this.bookRate = bookRate;
	    }

	    public String getClosingBookRate() {
	        return closingBookRate;
	    }

	    public void setClosingBookRate(String closingBookRate) {
	        this.closingBookRate = closingBookRate;
	    }

	    public String getBookAccumulatedDepreciation() {
	        return bookAccumulatedDepreciation;
	    }

	    public void setBookAccumulatedDepreciation(String bookAccumulatedDepreciation) {
	        this.bookAccumulatedDepreciation = bookAccumulatedDepreciation;
	    }

	    public String getBookDepMethodValue() {
	        return bookDepMethodValue;
	    }

	    public void setBookDepMethodValue(String bookDepMethodValue) {
	        this.bookDepMethodValue = bookDepMethodValue;
	    }
}

