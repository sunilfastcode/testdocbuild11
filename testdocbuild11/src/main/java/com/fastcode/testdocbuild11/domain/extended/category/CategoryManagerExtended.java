package com.fastcode.testdocbuild11.domain.extended.category;

import com.fastcode.testdocbuild11.domain.core.category.CategoryManager;
import com.fastcode.testdocbuild11.domain.extended.filmcategory.IFilmCategoryRepositoryExtended;
import org.springframework.stereotype.Component;

@Component("categoryManagerExtended")
public class CategoryManagerExtended extends CategoryManager implements ICategoryManagerExtended {

    public CategoryManagerExtended(
        ICategoryRepositoryExtended categoryRepositoryExtended,
        IFilmCategoryRepositoryExtended filmCategoryRepositoryExtended
    ) {
        super(categoryRepositoryExtended, filmCategoryRepositoryExtended);
    }
    //Add your custom code here
}
