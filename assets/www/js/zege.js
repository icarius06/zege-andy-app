ydn.debug.log('ydn.db', 'finest', document.getElementById('debug-console'));

var transactionsSchema = {
	name: "transaction",
	keyPath: "timeStamp",
	indexes: [
				{
	    			name: "timeStamp",
	    			unique: true
				}
			 ]
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

var getAllTransactionItems = function () {
  var df = db.values('transaction');
  df.done(function (items) {
    var n = items.length;
    var arr = [];
    
    for (var i = 0; i < n; i++) {
    	var toAdd ="{'units':"+items[i].units+",'timeStamp':"+items[i].timeStamp+",'amount':"+items[i].amount+",'particulars':"+items[i].particulars+",'priority':"+items[i].priority+"}";
    	arr.push(toAdd);    	
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
  var particulars = MyAndroid2.getTextValuesFor('particulars');
  var priority = MyAndroid2.getTextValuesFor('priority');
  
  var data = {
    "units":units,
    "amount":amount,
    "timeStamp":new Date().getTime(),
    "particulars":particulars,
    "priority":priority
  };
  
  db.put('transaction', data).fail(function(e) {
    throw e;
  });
  
  getAllTransactionItems();
};

function init() {
  getAllTransactionItems();

  document.addEventListener('pause', function() {
    db.close();
  });

  document.addEventListener('resume', function() {
  alert("tests");
    db = new ydn.db.Storage(db_name, schema);
  });
}

init();

