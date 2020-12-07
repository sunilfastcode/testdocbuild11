cube(`Store`, {
  sql: `SELECT * FROM public.store`,
  
  joins: {
    Address: {
      sql: `${CUBE}.address_id = ${Address}.address_id`,
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
		}
  },
  
  dimensions: {
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
	
    store_id: {
      sql: `store_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
