package org.d3if4011.matauangconverter.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if4011.matauangconverter.R
import org.d3if4011.matauangconverter.databinding.FragmentHitungBinding
import org.d3if4011.matauangconverter.db.ConverterDb
import org.d3if4011.matauangconverter.model.HasilConverter

class HitungFragment : Fragment() {
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private lateinit var binding: FragmentHitungBinding
    private val viewModel: HitungViewModel by lazy {
        val db = ConverterDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { idrToUSD() }
        binding.button2.setOnClickListener { idrToYen() }
        binding.button3.setOnClickListener { idrToEuro()}
        binding.shareButton.setOnClickListener { shareData() }
        viewModel.getHasilConveter().observe(requireActivity(), {showResult(it)})
    }

    private fun shareData() {
        val message = getString(R.string.bagikan_template,
        binding.jumlahUangInp.text)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }

    }

    private fun idrToUSD() {
        val jumlahUang = binding.jumlahUangInp.text.toString()
        if (TextUtils.isEmpty(jumlahUang)) {
            Toast.makeText(context, "Masukan jumlah uang.", Toast.LENGTH_LONG).show()
            return
        }
            viewModel.hitungConverter(
            jumlahUang.toFloat()
        )
    }

    private fun idrToYen() {
        val jumlahUang = binding.jumlahUangInp.text.toString()
        if (TextUtils.isEmpty(jumlahUang)) {
            Toast.makeText(context, "Masukan jumlah uang.", Toast.LENGTH_LONG).show()
            return
        }
            viewModel.hitungConverter(
            jumlahUang.toFloat()
        )
    }

    private fun idrToEuro() {
        val jumlahUang = binding.jumlahUangInp.text.toString()
        if (TextUtils.isEmpty(jumlahUang)) {
            Toast.makeText(context, "Masukan jumlah uang.", Toast.LENGTH_LONG).show()
            return
        }
            viewModel.hitungConverter(
            jumlahUang.toFloat()
        )
    }


    private fun showResult(result: HasilConverter?){
        if (result == null) return
            binding.hasil.text = getString(R.string.hasil_x, result.usd)
            binding.hasil.text = getString(R.string.hasil_x, result.yen)
            binding.hasil.text = getString(R.string.hasil_x, result.euro)
        binding.buttonGroup.visibility = View.VISIBLE
        }

    }