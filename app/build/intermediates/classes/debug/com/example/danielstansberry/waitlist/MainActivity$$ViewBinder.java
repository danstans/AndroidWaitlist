// Generated code from Butter Knife. Do not modify!
package com.example.danielstansberry.waitlist;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.example.danielstansberry.waitlist.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558522, "field 'username'");
    target.username = finder.castView(view, 2131558522, "field 'username'");
    view = finder.findRequiredView(source, 2131558520, "field 'numList'");
    target.numList = finder.castView(view, 2131558520, "field 'numList'");
    view = finder.findRequiredView(source, 2131558519, "field 'SendInfo'");
    target.SendInfo = finder.castView(view, 2131558519, "field 'SendInfo'");
    view = finder.findRequiredView(source, 2131558521, "field 'Retrieve'");
    target.Retrieve = finder.castView(view, 2131558521, "field 'Retrieve'");
    view = finder.findRequiredView(source, 2131558523, "field 'RestaurantList'");
    target.RestaurantList = finder.castView(view, 2131558523, "field 'RestaurantList'");
    view = finder.findRequiredView(source, 2131558524, "field 'confirmButton'");
    target.confirmButton = finder.castView(view, 2131558524, "field 'confirmButton'");
  }

  @Override public void unbind(T target) {
    target.username = null;
    target.numList = null;
    target.SendInfo = null;
    target.Retrieve = null;
    target.RestaurantList = null;
    target.confirmButton = null;
  }
}
