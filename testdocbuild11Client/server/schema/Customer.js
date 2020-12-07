cube(`Customer`, {
  sql: `SELECT * FROM public.customer`,
  
  joins: {
    Address: {
      sql: `${CUBE}.address_id = ${Address}.address_id`,
      relationship: `belongsTo`
    },
    
    Store: {
      sql: `${CUBE}.store_id = ${Store}.store_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		count_first_name: {
			sql: 'first_name',
			type: 'count'
		},
		countDistinct_first_name: {
			sql: 'first_name',
			type: 'countDistinct'
		},
		countDistinctApprox_first_name: {
			sql: 'first_name',
			type: 'countDistinctApprox'
		},
		count_last_name: {
			sql: 'last_name',
			type: 'count'
		},
		countDistinct_last_name: {
			sql: 'last_name',
			type: 'countDistinct'
		},
		countDistinctApprox_last_name: {
			sql: 'last_name',
			type: 'countDistinctApprox'
		},
		count_email: {
			sql: 'email',
			type: 'count'
		},
		countDistinct_email: {
			sql: 'email',
			type: 'countDistinct'
		},
		countDistinctApprox_email: {
			sql: 'email',
			type: 'countDistinctApprox'
		},
		count_activebool: {
			sql: 'activebool',
			type: 'count'
		},
		countDistinct_activebool: {
			sql: 'activebool',
			type: 'countDistinct'
		},
		countDistinctApprox_activebool: {
			sql: 'activebool',
			type: 'countDistinctApprox'
		},
		min_create_date: {
			sql: 'create_date',
			type: 'min'
		},
		max_create_date: {
			sql: 'create_date',
			type: 'max'
		},
		count_create_date: {
			sql: 'create_date',
			type: 'count'
		},
		countDistinct_create_date: {
			sql: 'create_date',
			type: 'countDistinct'
		},
		countDistinctApprox_create_date: {
			sql: 'create_date',
			type: 'countDistinctApprox'
		},
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
    firstName: {
      sql: `first_name`,
      type: `string`
    },
    
    lastName: {
      sql: `last_name`,
      type: `string`
    },
    
    email: {
      sql: `email`,
      type: `string`
    },
    
    activebool: {
      sql: `activebool`,
      type: `string`
    },
    
    createDate: {
      sql: `create_date`,
      type: `time`
    },
    
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
	
    customer_id: {
      sql: `customer_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
