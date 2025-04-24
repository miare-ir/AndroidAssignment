package ir.miare.androidcodechallenge.data

fun FakeData.toFakeDate(): ir.miare.androidcodechallenge.domain.FakeData = ir.miare.androidcodechallenge.domain.FakeData(
    league = this.league.toLeague(),
    players = this.players.map { it.toPlayer() }
)

fun League.toLeague(): ir.miare.androidcodechallenge.domain.League = ir.miare.androidcodechallenge.domain.League(
    name = this.name,
    country = this.country,
    rank = this.rank,
    totalMatches = this.totalMatches
)

fun Player.toPlayer(): ir.miare.androidcodechallenge.domain.Player = ir.miare.androidcodechallenge.domain.Player(
    name = this.name,
    team = this.team.toTeam(),
    totalGoal = this.totalGoal
)

fun Team.toTeam(): ir.miare.androidcodechallenge.domain.Team = ir.miare.androidcodechallenge.domain.Team(
    name = this.name,
    rank = this.rank
)