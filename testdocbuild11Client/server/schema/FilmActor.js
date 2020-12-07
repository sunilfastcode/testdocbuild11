cube(`FilmActor`, {
  sql: `SELECT * FROM public.film_actor`,
  
  joins: {
    Actor: {
      sql: `${CUBE}.actor_id = ${Actor}.actor_id`,
      relationship: `belongsTo`
    },
    
    Film: {
      sql: `${CUBE}.film_id = ${Film}.film_id`,
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
	id: {
		sql: `CONCAT(${CUBE}.film_id, ${CUBE}.actor_id)`,
		type: `number`,
		primaryKey: true
	  },
  }
});
