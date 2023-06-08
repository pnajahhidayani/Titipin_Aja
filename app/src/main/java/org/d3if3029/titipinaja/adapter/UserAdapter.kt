package org.d3if3029.titipinaja.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.d3if3029.titipinaja.R
import org.d3if3029.titipinaja.model.User

class UserAdapter(private var mContext: Context,
                  private var mUser: List<User>,
                  private var isFragment: Boolean = false)  : RecyclerView.Adapter<UserAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
       val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout, parent,false  )
        return UserAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = mUser[position]

        holder.userNameTextView.text = user.getUsername()
        holder.userFullnameTextView.text = user.getFullname()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfileImage)

    }

    override fun getItemCount(): Int {
        return mUser.size
    }


    class ViewHolder (@NonNull itemView: View)  : RecyclerView.ViewHolder(itemView)
    {
        var userNameTextView: TextView = itemView.findViewById(R.id.user_name_search)
        var userFullnameTextView: TextView = itemView.findViewById(R.id.user_full_name_search)
        var userProfileImage: CircleImageView= itemView.findViewById(R.id.user_profile_image_search)

    }
}
