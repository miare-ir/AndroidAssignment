package ir.miare.androidcodechallenge.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.logicbase.mockfit.Mock
import ir.logicbase.mockfit.MockFitConfig
import ir.logicbase.mockfit.MockFitInterceptor
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.data.LeagueData
import ir.miare.androidcodechallenge.databinding.FragmentRankingBinding
import ir.miare.androidcodechallenge.databinding.ItemLeagueBinding
import ir.miare.androidcodechallenge.databinding.ItemPlayerBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class RankingFragment(val sortingMode: Int) : Fragment() {

    interface Api {
        @Mock("data.json")
        @GET("list")
        fun getData(): Call<List<LeagueData>>
    }

    var binding: FragmentRankingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        binding = FragmentRankingBinding.bind(view)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://test_baseurl.com/v2/")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        MockFitInterceptor(
                            bodyFactory = { input -> resources.assets.open(input) },
                            logger = { tag, message -> Log.d(tag, message) },
                            baseUrl = "https://test_baseurl.com/v2/",
                            requestPathToMockPathRule = MockFitConfig.REQUEST_TO_JSON,
                            mockFilesPath = "",
                            mockFitEnable = true,
                            apiEnableMock = true,
                            apiIncludeIntoMock = arrayOf(),
                            apiExcludeFromMock = arrayOf(),
                            apiResponseLatency = 1000L
                        )
                    )
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()
            ).build()
        binding!!.pbLoading.show()
        thread(name = "NetworkWorker") {
            retrofit.create(Api::class.java).getData().enqueue(object : Callback<List<LeagueData>> {
                override fun onResponse(
                    call: Call<List<LeagueData>>,
                    response: Response<List<LeagueData>>
                ) {
                    val data = response.body()!!

                    if (sortingMode == -1) {
                        Handler(Looper.getMainLooper()).post {
                            binding!!.pbLoading.hide()
                            // 1
                            val league1 = ItemLeagueBinding.inflate(layoutInflater)
                            league1.leagueName.text = data[0].league.name
                            league1.leagueCountry.text = data[0].league.country
                            binding!!.linearLayout.addView(league1.root)

                            val player1 = data[0].players[0]
                            val playerItem1 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem1.playerName.text = player1.name
                            playerItem1.teamName.text = player1.team.name
                            playerItem1.rank.text = player1.team.rank.toString()
                            playerItem1.root.setOnClickListener {
                                PlayerInfoBottomSheet(player1).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem1.root)

                            val player2 = data[0].players[1]
                            val playerItem2 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem2.playerName.text = player2.name
                            playerItem2.teamName.text = player2.team.name
                            playerItem2.rank.text = player2.team.rank.toString()
                            playerItem2.root.setOnClickListener {
                                PlayerInfoBottomSheet(player2).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem2.root)

                            val player3 = data[0].players[2]
                            val playerItem3 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem3.playerName.text = player3.name
                            playerItem3.teamName.text = player3.team.name
                            playerItem3.rank.text = player3.team.rank.toString()
                            playerItem3.root.setOnClickListener {
                                PlayerInfoBottomSheet(player3).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem3.root)

                            // 2
                            val league2 = ItemLeagueBinding.inflate(layoutInflater)
                            league2.leagueName.text = data[1].league.name
                            league2.leagueCountry.text = data[1].league.country
                            binding!!.linearLayout.addView(league2.root)

                            val player4 = data[1].players[0]
                            val playerItem4 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem4.playerName.text = player4.name
                            playerItem4.teamName.text = player4.team.name
                            playerItem4.rank.text = player4.team.rank.toString()
                            playerItem4.root.setOnClickListener {
                                PlayerInfoBottomSheet(player4).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem4.root)

                            val player5 = data[1].players[1]
                            val playerItem5 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem5.playerName.text = player5.name
                            playerItem5.teamName.text = player5.team.name
                            playerItem5.rank.text = player5.team.rank.toString()
                            playerItem5.root.setOnClickListener {
                                PlayerInfoBottomSheet(player5).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem5.root)

                            val player6 = data[1].players[2]
                            val playerItem6 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem6.playerName.text = player6.name
                            playerItem6.teamName.text = player6.team.name
                            playerItem6.rank.text = player6.team.rank.toString()
                            playerItem6.root.setOnClickListener {
                                PlayerInfoBottomSheet(player6).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem6.root)

                            // 3
                            val league3 = ItemLeagueBinding.inflate(layoutInflater)
                            league3.leagueName.text = data[2].league.name
                            league3.leagueCountry.text = data[2].league.country
                            binding!!.linearLayout.addView(league3.root)

                            val player7 = data[2].players[0]
                            val playerItem7 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem7.playerName.text = player7.name
                            playerItem7.teamName.text = player7.team.name
                            playerItem7.rank.text = player7.team.rank.toString()
                            playerItem7.root.setOnClickListener {
                                PlayerInfoBottomSheet(player7).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem7.root)

                            val player8 = data[2].players[1]
                            val playerItem8 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem8.playerName.text = player8.name
                            playerItem8.teamName.text = player8.team.name
                            playerItem8.rank.text = player8.team.rank.toString()
                            playerItem8.root.setOnClickListener {
                                PlayerInfoBottomSheet(player8).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem8.root)

                            val player9 = data[2].players[2]
                            val playerItem9 = ItemPlayerBinding.inflate(layoutInflater)
                            playerItem9.playerName.text = player9.name
                            playerItem9.teamName.text = player9.team.name
                            playerItem9.rank.text = player9.team.rank.toString()
                            playerItem9.root.setOnClickListener {
                                PlayerInfoBottomSheet(player9).show(
                                    activity!!.supportFragmentManager,
                                    ""
                                )
                            }
                            binding!!.linearLayout.addView(playerItem9.root)
                        }
                    }
                }

                override fun onFailure(call: Call<List<LeagueData>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        return view
    }
}