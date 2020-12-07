cube(`Rental`, {
  sql: `SELECT * FROM public.rental`,
  
  joins: {
    Inventory: {
      sql: `${CUBE}.inventory_id = ${Inventory}.inventory_id`,
      relationship: `belongsTo`
    },
    
    Customer: {
      sql: `${CUBE}.customer_id = ${Customer}.customer_id`,
      relationship: `belongsTo`
    },
    
    Staff: {
      sql: `${CUBE}.staff_id = ${Staff}.staff_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		min_last_update: {
			sql: 'last_update',
			type: 'min'
		},
		max_last_update: {
			sql: 'last_update',
			type: 'max'
		},
		count_last_update: {
			sql: 'last_update',
			type: 'count'
		},
		countDistinct_last_update: {
			sql: 'last_update',
			type: 'countDistinct'
		},
		countDistinctApprox_last_update: {
			sql: 'last_update',
			type: 'countDistinctApprox'
		},
		min_rental_date: {
			sql: 'rental_date',
			type: 'min'
		},
		max_rental_date: {
			sql: 'rental_date',
			type: 'max'
		},
		count_rental_date: {
			sql: 'rental_date',
			type: 'count'
		},
		countDistinct_rental_date: {
			sql: 'rental_date',
			type: 'countDistinct'
		},
		countDistinctApprox_rental_date: {
			sql: 'rental_date',
			type: 'countDistinctApprox'
		},
		min_return_date: {
			sql: 'return_date',
			type: 'min'
		},
		max_return_date: {
			sql: 'return_date',
			type: 'max'
		},
		count_return_date: {
			sql: 'return_date',
			type: 'count'
		},
		countDistinct_return_date: {
			sql: 'return_date',
			type: 'countDistinct'
		},
		countDistinctApprox_return_date: {
			sql: 'return_date',
			type: 'countDistinctApprox'
		}
  },
  
  dimensions: {
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
    
    rentalDate: {
      sql: `rental_date`,
      type: `time`
    },
    
    returnDate: {
      sql: `return_date`,
      type: `time`
    },
	
    rental_id: {
      sql: `rental_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
