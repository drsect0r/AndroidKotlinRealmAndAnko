package com.donnfelker.kotlinmix

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView
import com.donnfelker.kotlinmix.models.Todo
import io.realm.Realm
import org.jetbrains.anko.find

class TodosFragment : Fragment(), TodoAdapter.TodoItemClickListener {

  companion object {
    fun newInstance(): TodosFragment {
      return TodosFragment()
    }
  }

  private var realm: Realm? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
    val v = inflater.inflate(R.layout.fragment_todos, container, false)
    //ButterKnife.bind(this, v)
    return v
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    realm = Realm.getDefaultInstance()
  }

  override fun onResume() {
    super.onResume()
    val todos = realm!!.where(Todo::class.java).findAll()
    val adapter = TodoAdapter(activity, todos, true, true, this)
    val rv: RealmRecyclerView? = find<RealmRecyclerView>(R.id.todos_recycler_view)
    //The !! Operator : Return Null Point Exception if rv is Null
    rv!!.setAdapter(adapter)
  }

  override fun onDestroy() {
    super.onDestroy()
    realm!!.close()
  }

  override fun onTodoClick(caller: View, task: Todo) {
    // EditFragment.Companion.newInstance call?
    // This is necessary because Kotlin does not have static methods.
    // Therefore, a companion object is necessary to accomplish a similar feat in Kotlin.
    val editFragment = EditFragment.newInstance(task.id!!)
    activity.fragmentManager
      .beginTransaction()
      .replace(R.id.content_main, editFragment, editFragment.javaClass.simpleName)
      .addToBackStack(editFragment.javaClass.simpleName)
      .commit()
  }
}
