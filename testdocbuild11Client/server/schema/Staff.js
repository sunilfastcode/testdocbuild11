cube(`Staff`, {
  sql: `SELECT * FROM public.staff`,
  
  joins: {
    Store: {
      sql: `${CUBE}.store_id = ${Store}.store_id`,
      relationship: `belongsTo`
    },
    
    Address: {
      sql: `${CUBE}.address_id = ${Address}.address_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		count_active: {
			sql: 'active',
			type: 'count'
		},
		countDistinct_active: {
			sql: 'active',
			type: 'countDistinct'
		},
		countDistinctApprox_active: {
			sql: 'active',
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
		count_username: {
			sql: 'username',
			type: 'count'
		},
		countDistinct_username: {
			sql: 'username',
			type: 'countDistinct'
		},
		countDistinctApprox_username: {
			sql: 'username',
			type: 'countDistinctApprox'
		},
		count_password: {
			sql: 'password',
			type: 'count'
		},
		countDistinct_password: {
			sql: 'password',
			type: 'countDistinct'
		},
		countDistinctApprox_password: {
			sql: 'password',
			type: 'countDistinctApprox'
		},
		count_picture: {
			sql: 'picture',
			type: 'count'
		},
		countDistinct_picture: {
			sql: 'picture',
			type: 'countDistinct'
		},
		countDistinctApprox_picture: {
			sql: 'picture',
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
    active: {
      sql: `active`,
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
    
    firstName: {
      sql: `first_name`,
      type: `string`
    },
    
    username: {
      sql: `username`,
      type: `string`
    },
    
    password: {
      sql: `password`,
      type: `string`
    },
    
    picture: {
      sql: `picture`,
      type: `string`
    },
    
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
	
    staff_id: {
      sql: `staff_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
