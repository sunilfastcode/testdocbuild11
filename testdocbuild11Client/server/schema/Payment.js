cube(`Payment`, {
  sql: `SELECT * FROM public.payment`,
  
  joins: {
    Customer: {
      sql: `${CUBE}.customer_id = ${Customer}.customer_id`,
      relationship: `belongsTo`
    },
    
    Staff: {
      sql: `${CUBE}.staff_id = ${Staff}.staff_id`,
      relationship: `belongsTo`
    },
    
    Rental: {
      sql: `${CUBE}.rental_id = ${Rental}.rental_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		min_payment_date: {
			sql: 'payment_date',
			type: 'min'
		},
		max_payment_date: {
			sql: 'payment_date',
			type: 'max'
		},
		count_payment_date: {
			sql: 'payment_date',
			type: 'count'
		},
		countDistinct_payment_date: {
			sql: 'payment_date',
			type: 'countDistinct'
		},
		countDistinctApprox_payment_date: {
			sql: 'payment_date',
			type: 'countDistinctApprox'
		}
  },
  
  dimensions: {
    paymentDate: {
      sql: `payment_date`,
      type: `time`
    },
	
    payment_id: {
      sql: `payment_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
