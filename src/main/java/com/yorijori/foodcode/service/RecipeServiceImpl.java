package com.yorijori.foodcode.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import com.yorijori.foodcode.jpa.entity.Recipe;
import com.yorijori.foodcode.jpa.entity.RecipeCategory;
import com.yorijori.foodcode.jpa.entity.RecipeImage;
import com.yorijori.foodcode.jpa.entity.RecipeIngredients;
import com.yorijori.foodcode.jpa.entity.RecipeQa;
import com.yorijori.foodcode.jpa.entity.RecipeReview;
import com.yorijori.foodcode.jpa.entity.UserInfo;
import com.yorijori.foodcode.jpa.entity.UserWishlist;
import com.yorijori.foodcode.repository.RecipeDAO;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
	RecipeDAO recipeDAO;

	@Autowired
	public RecipeServiceImpl(RecipeDAO recipeDAO) {
		super();
		this.recipeDAO = recipeDAO;
	}

	@Override
	public long countAll() {
		return recipeDAO.countAll();
	}

	@Override
	public List<Recipe> selectListByPage(int pageNo, int pagePerCount) {
		return recipeDAO.selectListByPage(pageNo, pagePerCount);
	}

	@Override
	public void wishList(UserInfo userId, Recipe recipeNo) {
		long count = recipeDAO.countByRcpSeqByWishList(recipeNo);
		UserWishlist userwishlist = new UserWishlist();
		userwishlist.setRecipeNo(recipeNo);
		userwishlist.setUserId(userId);
		if (count > 0) {
			recipeDAO.deleteWishList(recipeNo);
		} else {
			recipeDAO.addWishList(userwishlist);
		}
	}

	@Override
	public void viewCountUp(int recipeNo) {
		Recipe recipe = recipeDAO.findById(recipeNo);
		recipe.viewCountUp(recipe);
	}

	@Override
	public Recipe select(int recipeNo) {
		// TODO Auto-generated method stub
		return recipeDAO.select(recipeNo);
	}

    @Override
    public List<RecipeImage> imgselect(int recipeNo) {
        return recipeDAO.imgselect(recipeNo);
    }

    
    @Override
    public void insertAll(Recipe recipedata) {
    	List<RecipeIngredients> ingredients = recipedata.getIngrelist();
    	List<RecipeCategory> categorylist = recipedata.getCategorylist();
    	List<RecipeImage> imglist = recipedata.getImglist();

    	for(int i=0;i<ingredients.size(); i++) {
    		ingredients.get(i).setRecipeNo(recipedata);
    	}
    	for(int i=0;i<categorylist.size(); i++) {
    		categorylist.get(i).setRecipeNo(recipedata);
    	}
    	for(int i=0;i<imglist.size(); i++) {
    		imglist.get(i).setRecipeNo(recipedata);
    	}
    	recipeDAO.insertAll(recipedata);
    }

    @Override
    public List<Recipe> selectListByPageAndSort(int pageNo, int pagePerCount, String sortType){
    	return recipeDAO.selectListByPageAndSort(pageNo, pagePerCount, sortType);
    }
	@Override
	public List<RecipeReview> reviewselect(int recipeNo) {
		return recipeDAO.reviewselect(recipeNo);
	}

	@Override
	public void reviewsave(RecipeReview recipereview) {
	    recipeDAO.reviewsave(recipereview);
	}


	@Override
	public void recipeqasave(RecipeQa recipeqa) {
		recipeDAO.recipeqasave(recipeqa);
		
	}

	@Override
	public List<RecipeQa> QAselect(int recipeNo) {
		// TODO Auto-generated method stub
		return recipeDAO.QAselect(recipeNo);
	}

	@Override
	public List<RecipeIngredients> selectingr(int rcpSeq) {
		// TODO Auto-generated method stub
		return recipeDAO.selectingr(rcpSeq);
	}
	@Override
	public List<Recipe> findAll(Specification<Recipe> spec) {
		// TODO Auto-generated method stub
		return recipeDAO.findAll(spec);
	}
    public List<Recipe> findAll(Specification<Recipe> spec, Sort sort) {
        return recipeDAO.findAll(spec, sort);
    }

}
