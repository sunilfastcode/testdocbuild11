cube(`Actor`, {
  sql: `SELECT * FROM public.actor`,
  
  joins: {
    
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
    
    lastUpdate: {
      sql: `last_update`,
      type: `time`
	},
	
    actor_id: {
      sql: `actor_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
