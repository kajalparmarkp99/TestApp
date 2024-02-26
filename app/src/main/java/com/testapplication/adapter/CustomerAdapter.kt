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

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>(), Filterable {
    private var customers: List<Customer> = listOf()
    private var filteredCustomers: List<Customer> = listOf()


    fun setData(customers: List<Customer>) {
        this.customers = customers
        this.filteredCustomers = customers
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Customer>()
                val filterPattern = constraint?.toString()?.toLowerCase(Locale.ROOT)?.trim()

                filterPattern?.let {
                    if (it.isEmpty()) {
                        filteredList.addAll(customers)
                    } else {
                        customers.forEach { customer ->
                            if (customer.name.toLowerCase(Locale.ROOT).contains(filterPattern) ||
                                customer.mobile.contains(filterPattern)
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

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCustomers = results?.values as List<Customer>
                notifyDataSetChanged()
            }
        }
    }
    fun updateData(newList: List<Customer>) {
        customers = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customer_item, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.bind(customer)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.txtName)
        private val textViewMobile: TextView = itemView.findViewById(R.id.txtMobileNo)

        fun bind(customer: Customer) {
            textViewName.text = customer.name
            textViewMobile.text = customer.mobile
            // Bind other customer information here if needed
        }
    }

}