package vk.simulation;

import java.util.*;

import vk.view.AbstractView;

/**
 * Abstract model needed for the charts
 * @author Mark
 *
 */
public abstract class AbstractModel {

	protected List<AbstractView> views;

	public AbstractModel() {
		views = new ArrayList<AbstractView>();
	}

	public void addView(AbstractView view) {
		views.add(view);
	}

	public void notifyViews() {
		for(AbstractView v: this.views) v.updateView();
	}

}