package com.donnfelker.kotlinmix;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donnfelker.kotlinmix.models.Todo;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;


public class TodosFragment extends Fragment implements TodoAdapter.TodoItemClickListener {

  @Bind(R.id.todos_recycler_view)
  protected RealmRecyclerView rv;
  private Realm realm;

  public static TodosFragment newInstance() {
    return new TodosFragment();
  }

  public TodosFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_todos, container, false);
    ButterKnife.bind(this, v);
    return v;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    realm = Realm.getDefaultInstance();

  }

  @Override
  public void onResume() {
    super.onResume();
    RealmResults<Todo> todos = realm.where(Todo.class).findAll();
    TodoAdapter adapter = new TodoAdapter(getActivity(), todos, true, true, this);
    rv.setAdapter(adapter);

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  public void onTodoClick(View caller, Todo task) {
    // EditFragment.Companion.newInstance call?
    // This is necessary because Kotlin does not have static methods.
    // Therefore, a companion object is necessary to accomplish a similar feat in Kotlin.
    EditFragment editFragment = EditFragment.Companion.newInstance(task.getId());
    getActivity().getFragmentManager()
        .beginTransaction()
        .replace(R.id.content_main, editFragment, editFragment.getClass().getSimpleName())
        .addToBackStack(editFragment.getClass().getSimpleName())
        .commit();
  }
}
