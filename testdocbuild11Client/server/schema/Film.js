cube(`Film`, {
  sql: `SELECT * FROM public.film`,
  
  joins: {
    Language: {
      sql: `${CUBE}.language_id = ${Language}.language_id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
		count_description: {
			sql: 'description',
			type: 'count'
		},
		countDistinct_description: {
			sql: 'description',
			type: 'countDistinct'
		},
		countDistinctApprox_description: {
			sql: 'description',
			type: 'countDistinctApprox'
		},
		count_title: {
			sql: 'title',
			type: 'count'
		},
		countDistinct_title: {
			sql: 'title',
			type: 'countDistinct'
		},
		countDistinctApprox_title: {
			sql: 'title',
			type: 'countDistinctApprox'
		},
		count_rating: {
			sql: 'rating',
			type: 'count'
		},
		countDistinct_rating: {
			sql: 'rating',
			type: 'countDistinct'
		},
		countDistinctApprox_rating: {
			sql: 'rating',
			type: 'countDistinctApprox'
		},
		count_special_features: {
			sql: 'special_features',
			type: 'count'
		},
		countDistinct_special_features: {
			sql: 'special_features',
			type: 'countDistinct'
		},
		countDistinctApprox_special_features: {
			sql: 'special_features',
			type: 'countDistinctApprox'
		},
		count_fulltext: {
			sql: 'fulltext',
			type: 'count'
		},
		countDistinct_fulltext: {
			sql: 'fulltext',
			type: 'countDistinct'
		},
		countDistinctApprox_fulltext: {
			sql: 'fulltext',
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
    description: {
      sql: `description`,
      type: `string`
    },
    
    title: {
      sql: `title`,
      type: `string`
    },
    
    rating: {
      sql: `rating`,
      type: `string`
    },
    
    specialFeatures: {
      sql: `special_features`,
      type: `string`
    },
    
    fulltext: {
      sql: `fulltext`,
      type: `string`
    },
    
    lastUpdate: {
      sql: `last_update`,
      type: `time`
    },
	
    film_id: {
      sql: `film_id`,
      type: `number`,
      primaryKey: true
    }
  }
});
