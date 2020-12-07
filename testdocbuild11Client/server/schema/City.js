cube(`City`, {
  sql: `SELECT * FROM public.city`,
  
  joins: {
    Country: {
      sql: `${CUBE}.country_id = ${Country}.country_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		count_city: {
			sql: 'city',
			type: 'count'
		},
		countDistinct_city: {
			sql: 'city',
			type: 'countDistinct'
		},
		countDistinctApprox_city: {
			sql: 'city',
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
    city: {
      sql: `city`,
      type: `string`
    },
    
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
	
    city_id: {
      sql: `city_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
