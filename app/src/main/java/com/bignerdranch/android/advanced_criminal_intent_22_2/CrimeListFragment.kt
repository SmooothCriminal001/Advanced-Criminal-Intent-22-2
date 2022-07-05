package com.bignerdranch.android.advanced_criminal_intent_22_2

import CrimeListViewModel
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.advanced_criminal_intent_22_2.databinding.FragmentCrimeListBinding
import com.bignerdranch.android.advanced_criminal_intent_22_2.databinding.ListItemCrimeBinding
import com.bignerdranch.android.advanced_criminal_intent_22_2.databinding.ListItemCrimePoliceBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect

const val CRIME_DATE_FORMAT = "EEEE, MMM dd, yyyy"
class CrimeListFragment : Fragment() {

    private val viewModel: CrimeListViewModel by viewModels()
    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)

        val crimes = viewModel.crimes
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Things to be done in a coroutine tied to this fragment's lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
        	//Things that have to be done every time a fragment hits a specific lifecycle
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.crimes.collect{
                    binding.crimeRecyclerView.adapter = CrimeListAdapter().apply { submitList(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root){
    	
    	fun bind(crime : Crime){
    		binding.crimeTitle.text = crime.title
    		binding.crimeDate.text = DateFormat.format(CRIME_DATE_FORMAT, crime.date).toString()

    		binding.root.setOnClickListener{
    			Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
    		}

            binding.crimeSolved.visibility = canShowView(crime)
    	}
    }

    private inner class CrimePoliceViewHolder(private val binding: ListItemCrimePoliceBinding) : RecyclerView.ViewHolder(binding.root){

    	fun bind(crime : Crime){
    		//Assign binding instances with data here
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = DateFormat.format(CRIME_DATE_FORMAT, crime.date).toString()

            binding.root.setOnClickListener{
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.crimePoliceSolved.visibility = canShowView(crime)
    	}
    }

    fun canShowView(crime: Crime) : Int{
        return if(crime.isSolved) View.VISIBLE else View.GONE
    }

    object DiffUtilInstance : DiffUtil.ItemCallback<Crime>() {
    	override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
    		return oldItem.id == newItem.id		//Or any way to check the items are same
    	}
    
    	override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
    		return (oldItem.toString() == newItem.toString())
    	}
    }
    
    private inner class CrimeListAdapter : ListAdapter<Crime, RecyclerView.ViewHolder>(DiffUtilInstance){
    	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    		val inflater = LayoutInflater.from(parent.context)
    		when (viewType){
    			0 -> ListItemCrimeBinding.inflate(inflater, parent, false).also { return CrimeHolder(it) }
    			else -> ListItemCrimePoliceBinding.inflate(inflater, parent, false).also {return CrimePoliceViewHolder(it)}
    		}
    	}
    
    	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    		when (holder.itemViewType){
    			0 -> (holder as CrimeHolder).bind(getItem(position))
    			1 -> (holder as CrimePoliceViewHolder).bind(getItem(position))
    		}
    	}
    
    	override fun getItemViewType(position: Int): Int {
            val crime = getItem(position)
            if(crime.requiresPolice)
                return 1
            else
                return 0
    	}
    }
}