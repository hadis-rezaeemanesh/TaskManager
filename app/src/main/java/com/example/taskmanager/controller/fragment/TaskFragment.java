package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.LoginActivity;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.controller.adapter.TaskStateAdapter;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.repository.TaskDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskFragment extends Fragment {

    public static final String ARGS_TASK_STATE = "taskState";
    public static final String TAG_ADDING_TASK = "addingTask";
    public static final String ARGS_TASK_LIST_POSITION = "taskListPosition";
    public static final String ARGS_KEY_USER_ID = "keyUserId";
    public static final int REQUEST_CODE_SHOW_DETAIL = 0;
    public static final String FRAGMENT_TAG_SHOW_DETAIL = "showDetail";

    private FloatingActionButton mButtonAdd;
    private RecyclerView mRecyclerView;
    private LinearLayout mLayoutEmptyTask;
    private TaskAdapter mAdapter;
    private ImageView mImageView;

    private TaskStateAdapter mTaskStateAdapter;
    private String mTaskState;
    private  List<Task> mTasks = new ArrayList<>();
    private TaskDBRepository mRepository;
    private int mCurrentPosition;
    private Task mTask = new Task();
    private long mUserId;
    private String searchTitle;
    private int mPosition;

    private Callbacks mCallbacks;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(int position, long userId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_TASK_LIST_POSITION, position);
        args.putLong(ARGS_KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (mTasks.size() == 0)
            mCurrentPosition = getArguments().getInt(ARGS_TASK_LIST_POSITION);
        mUserId = getArguments().getLong(ARGS_KEY_USER_ID);
        mRepository = TaskDBRepository.getInstance(getActivity(), mCurrentPosition);
        mTasks = mRepository.getListWithPosition(mCurrentPosition);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_menu_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if(searchTitle != null) {
            searchItem.expandActionView();
            searchView.setQuery(searchTitle, true);
            mAdapter.getFilter().filter(searchTitle);
            searchView.clearFocus();

        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                searchView.setQuery(s, false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                mAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_menu_user:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.item_menu_delete:
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Are you sure you want to delete all of your tasks?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRepository.deleteAll(mUserId);
                        dialogInterface.cancel();
                        mCallbacks.onTaskListUpdated(0);
                        mCallbacks.onTaskListUpdated(1);
                        mCallbacks.onTaskListUpdated(2);

                    }
                });
                dialog.setNegativeButton("NO", null);
                dialog.create().show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        findViews(view);
        setListeners();
        initViews();
        return view;
    }

    private void findViews(View view) {
        mButtonAdd = view.findViewById(R.id.floating_action_button);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutEmptyTask = view.findViewById(R.id.linear_layout_empty_task);
        mImageView = view.findViewById(R.id.img_empty);
    }

    private void setListeners() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAddDialogFragment taskAddDialogFragment =
                        TaskAddDialogFragment.newInstance(mCurrentPosition);
                taskAddDialogFragment.show(
                        getActivity().getSupportFragmentManager(),
                        TAG_ADDING_TASK);
            }
        });
    }

    private void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mTasks.size() != 0){
            mLayoutEmptyTask.setVisibility(View.GONE);

            mTaskStateAdapter = new TaskStateAdapter(mTasks, getContext());
            mRecyclerView.setAdapter(mTaskStateAdapter);
            mTaskStateAdapter.notifyItemInserted(mTasks.size());
        }

    }

    private void setAdapterList() {
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(mTasks);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTasks(mTasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateUI() {
        mRepository.updateList();
        mTasks = mRepository.getListWithPosition(mPosition);
        setAdapterList();
        setEmptyList();
    }

    public void updateUI(int position){
        mRepository.updateList();
        mTasks = mRepository.getListWithPosition(position);
        mAdapter.notifyDataSetChanged();
        setAdapterList();
        setEmptyList();
    }

    private void updateUI(Task task, int position) {
        mRepository.updateTask(task);
        mRepository.updateList();
        mTasks = mRepository.getListWithPosition(mPosition);
        mAdapter.setTasks(mTasks);
        mAdapter.notifyDataSetChanged();
        setEmptyList();
    }

    private void setEmptyList() {

        if (mTasks.size() != 0)
            mLayoutEmptyTask.setVisibility(View.GONE);
        else if (mLayoutEmptyTask != null) {
            mLayoutEmptyTask.setVisibility(View.VISIBLE);
        }
    }

    class TaskHolder extends RecyclerView.ViewHolder{

        private TextView mTitle, mStartTitle, mDate;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  mPosition = mRepository.getCurrentPosition();
                    ShowDetailFragment showDetailFragment =
                            ShowDetailFragment.newInstance(mTask.getId(), mPosition);

                    showDetailFragment.setTargetFragment(
                            TaskFragment.this, REQUEST_CODE_SHOW_DETAIL);

                    showDetailFragment.show(
                            getActivity().getSupportFragmentManager(), FRAGMENT_TAG_SHOW_DETAIL);
                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitle.setText(task.getTitle());
            mStartTitle.setText(Character.toString(task.getTitle().charAt(0)));
            mDate.setText(task.getSimpleDate() + " " + task.getSimpleTime());
        }

        private void findViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.txt_view_title_item);
            mStartTitle = itemView.findViewById(R.id.title_first_word);
            mDate = itemView.findViewById(R.id.txt_view_date_item);
        }
    }


    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> implements Filterable {

        private List<Task> mTasks;
        private List<Task> mTaskList;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
            mTaskList = new ArrayList<>();
            notifyDataSetChanged();
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
            mTaskList = new ArrayList<>();
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TaskHolder(LayoutInflater
                    .from(getActivity()).inflate(R.layout.task_item_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            holder.bindTask(mTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        @Override
        public Filter getFilter() {
            return taskFilter;
        }

        private Filter taskFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List<Task> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0)
                    filteredList.addAll(mTaskList);
                else {
                    String filter = charSequence.toString().toLowerCase().trim();
                    searchTitle = filter;
                    for (Task task : mTaskList) {

                        if (task.getTitle().toLowerCase().contains(filter) ||
                                task.getDes().toLowerCase().contains(filter) ||
                                task.getSimpleDate().toLowerCase().contains(filter) ||
                                task.getSimpleTime().toLowerCase().contains(filter))
                            filteredList.add(task);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                if (mTasks != null)
                    mTasks.clear();
                else
                    mTasks = new ArrayList<>();

                if (filterResults != null)
                    mTasks.addAll((Collection<? extends Task>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == TaskPagerActivity.REQUEST_CODE_ADD_TASK) {
            mPosition = data.getIntExtra(TaskAddDialogFragment.EXTRA_NEW_TASK_POSITION, 0);
            mTasks = mRepository.getListWithPosition(mPosition);
            updateUI();

        } else if (requestCode == REQUEST_CODE_SHOW_DETAIL) {
            int position;
            Task newTask = (Task) data.getSerializableExtra(ShowDetailFragment.EXTRA_NEW_TASK);

            if (data.getBooleanExtra(ShowDetailFragment.EXTRA_EDIT_TEXT, false)) {
                position = newTask.getPosition();
                mRepository.updateTask(newTask);
            } else {
                position = data.getIntExtra(ShowDetailFragment.EXTRA_TASK_EDITED_CURRENT_POSITION, 0);
            }

            if (newTask != null) {
                updateUI(newTask, position);
            } else {
                int deletedTaskPosition = data.getIntExtra(
                        ShowDetailFragment.EXTRA_TASK_DELETED_CURRENT_POSITION, 0);
                mTasks = mRepository.getListWithPosition(deletedTaskPosition);
                updateUI();
            }
            if(newTask.getPosition() != mPosition && newTask != null) {
                mCallbacks.onTaskListUpdated(newTask.getPosition());
            }
        }
    }

    public interface Callbacks{
        void onTaskListUpdated(int position);
    }
}