ydn.debug.log('ydn.db', 'finest', document.getElementById('debug-console'));

var transactionsSchema = {
	name: "transaction",
	keyPath: "timeStamp",
	indexes: [{
	    	name: "timeStamp",
	    	unique: true
		}, {
	    	name: "units",
	    	unique: false
		}, {
	 	    name: "amount",
	    	unique: false
		}]
};

var schema = {
    stores: [transactionsSchema]
};

var db_name = 'transactions_2';

var options = {mechanisms: ['indexeddb']};

/**
 * Create and initialize the database.
 * @type {ydn.db.Storage}
 */
var db = new ydn.db.Storage(db_name, schema, options);

db.addEventListener('ready', function(e) {
  var err = e.getError();
  if (err) {
    throw err;
  } else {
    // console.log(db + 'ready');
  }
});

var deleteTransaction = function (id) {
  db.remove('transaction', id).fail(function(e) {
    throw e;
  });
  getAllTransactionItems();
};

var deleteTransactionByUnits = function(units) {
		var df = db.values('transaction');
  		df.done(function (items) {  		
    	  var n = items.length;
    	  for (var i = 0; i < n; i++) {
    	  		if(items[i].units==units){
    	  			db.remove('transaction', items[i].timeStamp).fail(function(e) {
			    		throw e;
			    	});
    	  		}
    	  }
    	});
    	getAllTransactionItems();
}

var getAllTransactionItems = function () {
  var df = db.values('transaction');
  df.done(function (items) {
    var n = items.length;
    var arr = [];
    
    for (var i = 0; i < n; i++) {
      arr.push(items[i].units);
    }
    MyAndroid.loadAllTransactions(arr);
  });

  df.fail(function (e) {
    throw e;
  })
};

var addTransaction = function () {
  var units = MyAndroid2.getTextValuesFor('units');
  var amount = MyAndroid2.getTextValuesFor('amount');
  
  var data = {
    "units":units,
    "amount":amount,
    "timeStamp":new Date().getTime()
  };
  
  db.put('transaction', data).fail(function(e) {
    throw e;
  });
  
  getAllTransactionItems();
};

var renderTransaction = function (row) {
  var transactions = document.getElementById("transactionItems");
  var li = document.createElement("li");
  var a = document.createElement("a");
  var t = document.createTextNode(row.units);

  a.addEventListener("click", function () {
    deleteTransaction(row.timeStamp);
  }, false);

  a.textContent = " [Delete]";
  li.appendChild(t);
  li.appendChild(a);
  transactions.appendChild(li)
};

function init() {
  getAllTransactionItems();

  document.addEventListener('pause', function() {
    db.close();
  });

  document.addEventListener('resume', function() {
    db = new ydn.db.Storage(db_name, schema);
  });
}

init();

