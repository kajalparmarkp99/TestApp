package com.testapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testapplication.R
import com.testapplication.model.Customer
import java.util.Locale

class CustomerAdapter(private var customersList: List<Customer?>) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>(), Filterable {

    private var filteredCustomers: List<Customer?> = listOf()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Customer?>()

                val filterPattern = constraint?.toString()?.lowercase(Locale.ROOT)?.trim()
                filterPattern?.let {
                    if (it.isEmpty()) {
                        filteredList.addAll(customersList)
                    } else {
                        customersList.forEach { customer ->
                            if (customer?.name?.lowercase(Locale.ROOT)
                                    ?.contains(filterPattern) == true ||
                                customer?.mobile?.contains(filterPattern) == true
                            ) {
                                filteredList.add(customer)
                            }
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCustomers = results?.values as List<Customer?>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.customer_item, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customersList[position]
        if (customer != null) {
            holder.bind(customer)
        }
    }

    override fun getItemCount(): Int {
        return customersList.size
    }

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.txtName)
        private val textViewMobile: TextView = itemView.findViewById(R.id.txtMobileNo)
        private val textViewImage: TextView = itemView.findViewById(R.id.imageText)
        private val textEmail: TextView = itemView.findViewById(R.id.txtEmail)

        fun bind(customer: Customer) {
            textViewName.text = customer.name
            textViewMobile.text = customer.mobile
            if (!customer.email.isNullOrEmpty()) {
                textEmail.text = customer.email
            }

            if (!customer.name.isNullOrEmpty()) {
                val initials = if (customer.name.contains(" ")) {
                    val words = customer.name.split(" ")
                    val nonEmptyWords = words.filter { it.isNotEmpty() }
                    if (nonEmptyWords.isNotEmpty()) {
                        nonEmptyWords.map { it.first() }.joinToString("")
                    } else {
                        ""
                    }
                } else {
                    customer.name.first().toString()
                }
                // Set the initials to your TextView or wherever you want to display them
                textViewImage.text = initials.uppercase()
            }
        }
    }

}