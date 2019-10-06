package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

   // private Context mContext;
    private List<ToDo> toDoList;

//    public TodoAdapter(Context mContext, List<ToDo> toDoList){
//        this.mContext = mContext;
//        this.toDoList = toDoList;
//    }

    public interface OnItemOnClickListener{
        void onItemOnClick(View view,int pos);
        void onItemLongOnClick(View view ,int pos);
    }

    //设置监听的方法和声明了一个这个接口的内部变量    供外部来设置监听
    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }

    //删除函数
    public void removeItem(int pos){
        toDoList.remove(pos);
        notifyItemRemoved(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView todoImage;
        TextView todoName;
        TextView todoLevel;
        TextView todoTime;
        TextView todoSaveTime;

        public ViewHolder(View view){
            super(view);
            todoImage=view.findViewById(R.id.todo_image);
            todoName=view.findViewById(R.id.todo_name);
            todoLevel=view.findViewById(R.id.todo_level);
            todoTime=view.findViewById(R.id.todo_time);
            todoSaveTime=view.findViewById(R.id.todo_save_name);
        }

    }

    public TodoAdapter(List<ToDo> toDoList1){
        toDoList=toDoList1;
    }


    //创建viewholder实例，将todo-item的布局加载，并传到构造函数中
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        ToDo toDo=toDoList.get(position);//得到当前todo实例
        //对recyclerview的子项数据进行赋值
        holder.todoImage.setImageResource(toDo.getTodoID());
        holder.todoName.setText(toDo.getTodoName());
        holder.todoLevel.setText(toDo.getTodoLevel());
        holder.todoTime.setText(toDo.getTodoTime());
        holder.todoSaveTime.setText(toDo.getTodoSaveTime());

//        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.getTextView().setText(infos.get(position));

        if(mOnItemOnClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemOnClickListener.onItemOnClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemOnClickListener.onItemLongOnClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }

    //返回todolist的大小
    @Override
    public int getItemCount() {
        return toDoList.size();
    }

}
