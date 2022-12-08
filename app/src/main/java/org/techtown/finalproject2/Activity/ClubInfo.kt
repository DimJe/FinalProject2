package org.techtown.finalproject2.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.API.ClubData
import org.techtown.finalproject2.R
import org.techtown.finalproject2.adapter.ClubAdapter
import org.techtown.finalproject2.databinding.ActivityClubInfoBinding
import org.techtown.finalproject2.databinding.DialogClubBinding
import org.techtown.finalproject2.databinding.RecyclerClubItemBinding

class ClubInfo : AppCompatActivity() {

    private val binding : ActivityClubInfoBinding by lazy {
        ActivityClubInfoBinding.inflate(layoutInflater)
    }
    private val api : Api by inject()
    private var selectClub : ClubData? = null
    val user = api.user!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }
    private fun initView(){

        setSupportActionBar(binding.clubInfo)
        ClubAdapter(api.clubDataList).apply {
            binding.recycler.adapter = this
            this.setOnItemClickListener(object : ClubAdapter.OnItemClickListener{
                override fun onItemClick(b: RecyclerClubItemBinding, club: ClubData, positon: Int) {
                    Log.d("태그", "onItemClick: call")
                    binding.select.text = "선택한 클럽 명 : ${club.club_NM}"
                    selectClub = club

                }
            })
            binding.recycler.layoutManager = LinearLayoutManager(this@ClubInfo)
        }

        binding.joinClub.setOnClickListener {
            if(selectClub != null && api.user?.club_NO != selectClub!!.club_NO_PK){
                CoroutineScope(Dispatchers.IO).launch {
                    api.modifyUser(user.user_NM,user.user_GNDR,user.user_AGE,user.user_NCKNM,user.user_RGN,user.user_TELNO,user.user_EML_ADDR,user.user_HGT,user.user_WGT,user.instr_USER_YN,user.user_MNR_SCR,user.user_SKL_SCR,selectClub!!.club_NO_PK)
                    delay(1000)
                    if(api.getUser(user.user_EML_ADDR)) finish()
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_club,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val dialogBinding = DialogClubBinding.inflate(layoutInflater)
        Log.d("태그", "onOptionsItemSelected: 클릭")
        var sport  = ""
        dialogBinding.sportType.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.football -> sport = "축구"
                R.id.basket -> sport = "농구"
                R.id.all -> sport = "종합"
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("클럽 등록")
            .setPositiveButton("등록"){_,_ ->
                if(sport.isNotBlank() && dialogBinding.maxNum.text.toString().toIntOrNull() != null && dialogBinding.clubName.text.toString().isNotBlank()){
                    api.registerClub(dialogBinding.clubName.text.toString(),sport,api.user?.user_RGN!!,dialogBinding.maxNum.text.toString().toInt(),api.user?.user_EML_ADDR!!)
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(500)
                        val num = api.clubDataList.find {
                            it.club_NM == dialogBinding.clubName.text.toString()
                        }?.club_NO_PK
                        Log.d("태그", "onOptionsItemSelected:$num ")
                        api.modifyUser(user.user_NM,user.user_GNDR,user.user_AGE,user.user_NCKNM,user.user_RGN,user.user_TELNO,user.user_EML_ADDR,user.user_HGT,user.user_WGT,user.instr_USER_YN,user.user_MNR_SCR,user.user_SKL_SCR,num!!)
                        delay(1000)
                        api.getUser(user.user_EML_ADDR)
                        finish()
                    }
                }

            }
            .setNegativeButton("취소"){_,_ ->}
            .show()

        return true
    }
}