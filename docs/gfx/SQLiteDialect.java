


<!DOCTYPE html>
<html lang="en" class="">
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# object: http://ogp.me/ns/object# article: http://ogp.me/ns/article# profile: http://ogp.me/ns/profile#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Language" content="en">
    
    
    <title>sqlite-dialect/SQLiteDialect.java at master · gwenn/sqlite-dialect</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub">
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub">
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png">
    <meta property="fb:app_id" content="1401488693436528">

      <meta content="@github" name="twitter:site" /><meta content="summary" name="twitter:card" /><meta content="gwenn/sqlite-dialect" name="twitter:title" /><meta content="sqlite-dialect - Hibernate dialect for SQLite" name="twitter:description" /><meta content="https://avatars2.githubusercontent.com/u/45554?v=3&amp;s=400" name="twitter:image:src" />
      <meta content="GitHub" property="og:site_name" /><meta content="object" property="og:type" /><meta content="https://avatars2.githubusercontent.com/u/45554?v=3&amp;s=400" property="og:image" /><meta content="gwenn/sqlite-dialect" property="og:title" /><meta content="https://github.com/gwenn/sqlite-dialect" property="og:url" /><meta content="sqlite-dialect - Hibernate dialect for SQLite" property="og:description" />
      <meta name="browser-stats-url" content="/_stats">
    <link rel="assets" href="https://assets-cdn.github.com/">
    <link rel="conduit-xhr" href="https://ghconduit.com:25035">
    <link rel="xhr-socket" href="/_sockets">
    <meta name="pjax-timeout" content="1000">
    <link rel="sudo-modal" href="/sessions/sudo_modal">

    <meta name="msapplication-TileImage" content="/windows-tile.png">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="selected-link" value="repo_source" data-pjax-transient>
      <meta name="google-analytics" content="UA-3769691-2">

    <meta content="collector.githubapp.com" name="octolytics-host" /><meta content="collector-cdn.github.com" name="octolytics-script-host" /><meta content="github" name="octolytics-app-id" /><meta content="53F887C8:202A:227BF24A:54DE2AF8" name="octolytics-dimension-request_id" /><meta content="545943" name="octolytics-actor-id" /><meta content="ludwigandersson" name="octolytics-actor-login" /><meta content="5f4a5221ee305722b54da4779b6d801642702975e728f14d384739f8de805eef" name="octolytics-actor-hash" />
    
    <meta content="Rails, view, blob#show" name="analytics-event" />

    
    
    <link rel="icon" type="image/x-icon" href="https://assets-cdn.github.com/favicon.ico">


    <meta content="authenticity_token" name="csrf-param" />
<meta content="+NUYQbsfe4ZBl0GPYQdG0CVu/VM5DayYMGHQVRaKVdWQTGTQBs7WcMNyLizWUjdCwTw8Lb7/Wzxk3+g53wq8SQ==" name="csrf-token" />

    <link href="https://assets-cdn.github.com/assets/github-7f7a8d43d99dce26334cc1cb3b327f57a9309f9d6f215d6f3dff77d5e0c593a3.css" media="all" rel="stylesheet" />
    <link href="https://assets-cdn.github.com/assets/github2-a8f2a7df6fdc952a46bc2ca532f6648544a4e0a803dba39a318d244baa77bbd5.css" media="all" rel="stylesheet" />
    
    


    <meta http-equiv="x-pjax-version" content="fd342b33cafb8eda850e20ccf0d49a4d">

      
  <meta name="description" content="sqlite-dialect - Hibernate dialect for SQLite">
  <meta name="go-import" content="github.com/gwenn/sqlite-dialect git https://github.com/gwenn/sqlite-dialect.git">

  <meta content="45554" name="octolytics-dimension-user_id" /><meta content="gwenn" name="octolytics-dimension-user_login" /><meta content="109754" name="octolytics-dimension-repository_id" /><meta content="gwenn/sqlite-dialect" name="octolytics-dimension-repository_nwo" /><meta content="true" name="octolytics-dimension-repository_public" /><meta content="false" name="octolytics-dimension-repository_is_fork" /><meta content="109754" name="octolytics-dimension-repository_network_root_id" /><meta content="gwenn/sqlite-dialect" name="octolytics-dimension-repository_network_root_nwo" />
  <link href="https://github.com/gwenn/sqlite-dialect/commits/master.atom" rel="alternate" title="Recent Commits to sqlite-dialect:master" type="application/atom+xml">

  </head>


  <body class="logged_in  env-production macintosh vis-public page-blob">
    <a href="#start-of-content" tabindex="1" class="accessibility-aid js-skip-to-content">Skip to content</a>
    <div class="wrapper">
      
      
      
      


      <div class="header header-logged-in true" role="banner">
  <div class="container clearfix">

    <a class="header-logo-invertocat" href="https://github.com/" data-hotkey="g d" aria-label="Homepage" ga-data-click="Header, go to dashboard, icon:logo">
  <span class="mega-octicon octicon-mark-github"></span>
</a>


      <div class="site-search repo-scope js-site-search" role="search">
          <form accept-charset="UTF-8" action="/gwenn/sqlite-dialect/search" class="js-site-search-form" data-global-search-url="/search" data-repo-search-url="/gwenn/sqlite-dialect/search" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
  <input type="text"
    class="js-site-search-field is-clearable"
    data-hotkey="s"
    name="q"
    placeholder="Search"
    data-global-scope-placeholder="Search GitHub"
    data-repo-scope-placeholder="Search"
    tabindex="1"
    autocapitalize="off">
  <div class="scope-badge">This repository</div>
</form>
      </div>
      <ul class="header-nav left" role="navigation">
        <li class="header-nav-item explore">
          <a class="header-nav-link" href="/explore" data-ga-click="Header, go to explore, text:explore">Explore</a>
        </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="https://gist.github.com" data-ga-click="Header, go to gist, text:gist">Gist</a>
          </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="/blog" data-ga-click="Header, go to blog, text:blog">Blog</a>
          </li>
        <li class="header-nav-item">
          <a class="header-nav-link" href="https://help.github.com" data-ga-click="Header, go to help, text:help">Help</a>
        </li>
      </ul>

    
<ul class="header-nav user-nav right" id="user-links">
  <li class="header-nav-item dropdown js-menu-container">
    <a class="header-nav-link name" href="/ludwigandersson" data-ga-click="Header, go to profile, text:username">
      <img alt="Ludwig Andersson" class="avatar" data-user="545943" height="20" src="https://avatars3.githubusercontent.com/u/545943?v=3&amp;s=40" width="20" />
      <span class="css-truncate">
        <span class="css-truncate-target">ludwigandersson</span>
      </span>
    </a>
  </li>

  <li class="header-nav-item dropdown js-menu-container">
    <a class="header-nav-link js-menu-target tooltipped tooltipped-s" href="#" aria-label="Create new..." data-ga-click="Header, create new, icon:add">
      <span class="octicon octicon-plus"></span>
      <span class="dropdown-caret"></span>
    </a>

    <div class="dropdown-menu-content js-menu-content">
      
<ul class="dropdown-menu">
  <li>
    <a href="/new" data-ga-click="Header, create new repository, icon:repo"><span class="octicon octicon-repo"></span> New repository</a>
  </li>
  <li>
    <a href="/organizations/new" data-ga-click="Header, create new organization, icon:organization"><span class="octicon octicon-organization"></span> New organization</a>
  </li>


    <li class="dropdown-divider"></li>
    <li class="dropdown-header">
      <span title="gwenn/sqlite-dialect">This repository</span>
    </li>
      <li>
        <a href="/gwenn/sqlite-dialect/issues/new" data-ga-click="Header, create new issue, icon:issue"><span class="octicon octicon-issue-opened"></span> New issue</a>
      </li>
</ul>

    </div>
  </li>

  <li class="header-nav-item">
        <a href="/notifications" aria-label="You have no unread notifications" class="header-nav-link notification-indicator tooltipped tooltipped-s" data-ga-click="Header, go to notifications, icon:read" data-hotkey="g n">
        <span class="mail-status all-read"></span>
        <span class="octicon octicon-inbox"></span>
</a>
  </li>

  <li class="header-nav-item">
    <a class="header-nav-link tooltipped tooltipped-s" href="/settings/profile" id="account_settings" aria-label="Settings" data-ga-click="Header, go to settings, icon:settings">
      <span class="octicon octicon-gear"></span>
    </a>
  </li>

  <li class="header-nav-item">
    <form accept-charset="UTF-8" action="/logout" class="logout-form" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="oMpz0F4cpSpBK0M2AWOeXuggzs2yIYYUKMqfPGuIh0zLtm4KbvwzyR+ZunRYfFUarzZmf7IefZA+WvAuOcFERQ==" /></div>
      <button class="header-nav-link sign-out-button tooltipped tooltipped-s" aria-label="Sign out" data-ga-click="Header, sign out, icon:logout">
        <span class="octicon octicon-sign-out"></span>
      </button>
</form>  </li>

</ul>


    
  </div>
</div>

      

        


      <div id="start-of-content" class="accessibility-aid"></div>
          <div class="site" itemscope itemtype="http://schema.org/WebPage">
    <div id="js-flash-container">
      
    </div>
    <div class="pagehead repohead instapaper_ignore readability-menu">
      <div class="container">
        
<ul class="pagehead-actions">

  <li>
      <form accept-charset="UTF-8" action="/notifications/subscribe" class="js-social-container" data-autosubmit="true" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="u+6YKXPHyoB+IfhdXZ8B9rUXsukDtzBwW2AX8x6t5C7VXPUB/oFlJsJ60WlXQjpxtnNBDIeTJkT/+MKJsozafQ==" /></div>    <input id="repository_id" name="repository_id" type="hidden" value="109754" />

      <div class="select-menu js-menu-container js-select-menu">
        <a class="social-count js-social-count" href="/gwenn/sqlite-dialect/watchers">
          5
        </a>
        <a href="/gwenn/sqlite-dialect/subscription"
          class="minibutton select-menu-button with-count js-menu-target" role="button" tabindex="0" aria-haspopup="true">
          <span class="js-select-button">
            <span class="octicon octicon-eye"></span>
            Watch
          </span>
        </a>

        <div class="select-menu-modal-holder">
          <div class="select-menu-modal subscription-menu-modal js-menu-content" aria-hidden="true">
            <div class="select-menu-header">
              <span class="select-menu-title">Notifications</span>
              <span class="octicon octicon-x js-menu-close" role="button" aria-label="Close"></span>
            </div>

            <div class="select-menu-list js-navigation-container" role="menu">

              <div class="select-menu-item js-navigation-item selected" role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input checked="checked" id="do_included" name="do" type="radio" value="included" />
                  <span class="select-menu-item-heading">Not watching</span>
                  <span class="description">Be notified when participating or @mentioned.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-eye"></span>
                    Watch
                  </span>
                </div>
              </div>

              <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input id="do_subscribed" name="do" type="radio" value="subscribed" />
                  <span class="select-menu-item-heading">Watching</span>
                  <span class="description">Be notified of all conversations.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-eye"></span>
                    Unwatch
                  </span>
                </div>
              </div>

              <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input id="do_ignore" name="do" type="radio" value="ignore" />
                  <span class="select-menu-item-heading">Ignoring</span>
                  <span class="description">Never be notified.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-mute"></span>
                    Stop ignoring
                  </span>
                </div>
              </div>

            </div>

          </div>
        </div>
      </div>
</form>

  </li>

  <li>
    
  <div class="js-toggler-container js-social-container starring-container ">

    <form accept-charset="UTF-8" action="/gwenn/sqlite-dialect/unstar" class="js-toggler-form starred js-unstar-button" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="PpmLsg6RUFMzZe9LbQ5Plv/RuFYOy0zCRLC1qOg3duEBGXZYI2TqPXUyJqNvMqn3juygl7GAsMSQs0ex1gpX3Q==" /></div>
      <button
        class="minibutton with-count js-toggler-target"
        aria-label="Unstar this repository" title="Unstar gwenn/sqlite-dialect">
        <span class="octicon octicon-star"></span>
        Unstar
      </button>
        <a class="social-count js-social-count" href="/gwenn/sqlite-dialect/stargazers">
          17
        </a>
</form>
    <form accept-charset="UTF-8" action="/gwenn/sqlite-dialect/star" class="js-toggler-form unstarred js-star-button" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="EDmWR99VsnbYuDr7m9R5e/n7GnB1BxmkZiL03vkDLZdK0C8Sxl7dtR4rYgWn6fZ/4vX5NluHC2ne7+T/bDbG0g==" /></div>
      <button
        class="minibutton with-count js-toggler-target"
        aria-label="Star this repository" title="Star gwenn/sqlite-dialect">
        <span class="octicon octicon-star"></span>
        Star
      </button>
        <a class="social-count js-social-count" href="/gwenn/sqlite-dialect/stargazers">
          17
        </a>
</form>  </div>

  </li>

        <li>
          <a href="/gwenn/sqlite-dialect/fork" class="minibutton with-count js-toggler-target tooltipped-n" title="Fork your own copy of gwenn/sqlite-dialect to your account" aria-label="Fork your own copy of gwenn/sqlite-dialect to your account" rel="nofollow" data-method="post">
            <span class="octicon octicon-repo-forked"></span>
            Fork
          </a>
          <a href="/gwenn/sqlite-dialect/network" class="social-count">31</a>
        </li>

</ul>

        <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
          <span class="mega-octicon octicon-repo"></span>
          <span class="author"><a href="/gwenn" class="url fn" itemprop="url" rel="author"><span itemprop="title">gwenn</span></a></span><!--
       --><span class="path-divider">/</span><!--
       --><strong><a href="/gwenn/sqlite-dialect" class="js-current-repository" data-pjax="#js-repo-pjax-container">sqlite-dialect</a></strong>

          <span class="page-context-loader">
            <img alt="" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
          </span>

        </h1>
      </div><!-- /.container -->
    </div><!-- /.repohead -->

    <div class="container">
      <div class="repository-with-sidebar repo-container new-discussion-timeline  ">
        <div class="repository-sidebar clearfix">
            
<nav class="sunken-menu repo-nav js-repo-nav js-sidenav-container-pjax js-octicon-loaders"
     role="navigation"
     data-pjax="#js-repo-pjax-container"
     data-issue-count-url="/gwenn/sqlite-dialect/issues/counts">
  <ul class="sunken-menu-group">
    <li class="tooltipped tooltipped-w" aria-label="Code">
      <a href="/gwenn/sqlite-dialect" aria-label="Code" class="selected js-selected-navigation-item sunken-menu-item" data-hotkey="g c" data-selected-links="repo_source repo_downloads repo_commits repo_releases repo_tags repo_branches /gwenn/sqlite-dialect">
        <span class="octicon octicon-code"></span> <span class="full-word">Code</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>

      <li class="tooltipped tooltipped-w" aria-label="Issues">
        <a href="/gwenn/sqlite-dialect/issues" aria-label="Issues" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g i" data-selected-links="repo_issues repo_labels repo_milestones /gwenn/sqlite-dialect/issues">
          <span class="octicon octicon-issue-opened"></span> <span class="full-word">Issues</span>
          <span class="js-issue-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>      </li>

    <li class="tooltipped tooltipped-w" aria-label="Pull Requests">
      <a href="/gwenn/sqlite-dialect/pulls" aria-label="Pull Requests" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g p" data-selected-links="repo_pulls /gwenn/sqlite-dialect/pulls">
          <span class="octicon octicon-git-pull-request"></span> <span class="full-word">Pull Requests</span>
          <span class="js-pull-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>


  </ul>
  <div class="sunken-menu-separator"></div>
  <ul class="sunken-menu-group">

    <li class="tooltipped tooltipped-w" aria-label="Pulse">
      <a href="/gwenn/sqlite-dialect/pulse" aria-label="Pulse" class="js-selected-navigation-item sunken-menu-item" data-selected-links="pulse /gwenn/sqlite-dialect/pulse">
        <span class="octicon octicon-pulse"></span> <span class="full-word">Pulse</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>

    <li class="tooltipped tooltipped-w" aria-label="Graphs">
      <a href="/gwenn/sqlite-dialect/graphs" aria-label="Graphs" class="js-selected-navigation-item sunken-menu-item" data-selected-links="repo_graphs repo_contributors /gwenn/sqlite-dialect/graphs">
        <span class="octicon octicon-graph"></span> <span class="full-word">Graphs</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>
  </ul>


</nav>

              <div class="only-with-full-nav">
                  
<div class="clone-url open"
  data-protocol-type="http"
  data-url="/users/set_protocol?protocol_selector=http&amp;protocol_type=clone">
  <h3><span class="text-emphasized">HTTPS</span> clone URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/gwenn/sqlite-dialect.git" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  
<div class="clone-url "
  data-protocol-type="ssh"
  data-url="/users/set_protocol?protocol_selector=ssh&amp;protocol_type=clone">
  <h3><span class="text-emphasized">SSH</span> clone URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="git@github.com:gwenn/sqlite-dialect.git" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  
<div class="clone-url "
  data-protocol-type="subversion"
  data-url="/users/set_protocol?protocol_selector=subversion&amp;protocol_type=clone">
  <h3><span class="text-emphasized">Subversion</span> checkout URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/gwenn/sqlite-dialect" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>



<p class="clone-options">You can clone with
  <a href="#" class="js-clone-selector" data-protocol="http">HTTPS</a>, <a href="#" class="js-clone-selector" data-protocol="ssh">SSH</a>, or <a href="#" class="js-clone-selector" data-protocol="subversion">Subversion</a>.
  <a href="https://help.github.com/articles/which-remote-url-should-i-use" class="help tooltipped tooltipped-n" aria-label="Get help on which URL is right for you.">
    <span class="octicon octicon-question"></span>
  </a>
</p>

  <a href="http://mac.github.com" data-url="github-mac://openRepo/https://github.com/gwenn/sqlite-dialect" class="minibutton sidebar-button js-conduit-rewrite-url" title="Save gwenn/sqlite-dialect to your computer and use it in GitHub Desktop." aria-label="Save gwenn/sqlite-dialect to your computer and use it in GitHub Desktop.">
    <span class="octicon octicon-device-desktop"></span>
    Clone in Desktop
  </a>


                <a href="/gwenn/sqlite-dialect/archive/master.zip"
                   class="minibutton sidebar-button"
                   aria-label="Download the contents of gwenn/sqlite-dialect as a zip file"
                   title="Download the contents of gwenn/sqlite-dialect as a zip file"
                   rel="nofollow">
                  <span class="octicon octicon-cloud-download"></span>
                  Download ZIP
                </a>
              </div>
        </div><!-- /.repository-sidebar -->

        <div id="js-repo-pjax-container" class="repository-content context-loader-container" data-pjax-container>
          

<a href="/gwenn/sqlite-dialect/blob/c165e9273d4bb606b223e215072d460df3c1f81a/src/main/java/org/hibernate/dialect/SQLiteDialect.java" class="hidden js-permalink-shortcut" data-hotkey="y">Permalink</a>

<!-- blob contrib key: blob_contributors:v21:8b195b4b3ef41d9952dc9467b6302109 -->

<div class="file-navigation js-zeroclipboard-container">
  
<div class="select-menu js-menu-container js-select-menu left">
  <span class="minibutton select-menu-button js-menu-target css-truncate" data-hotkey="w"
    data-master-branch="master"
    data-ref="master"
    title="master"
    role="button" aria-label="Switch branches or tags" tabindex="0" aria-haspopup="true">
    <span class="octicon octicon-git-branch"></span>
    <i>branch:</i>
    <span class="js-select-button css-truncate-target">master</span>
  </span>

  <div class="select-menu-modal-holder js-menu-content js-navigation-container" data-pjax aria-hidden="true">

    <div class="select-menu-modal">
      <div class="select-menu-header">
        <span class="select-menu-title">Switch branches/tags</span>
        <span class="octicon octicon-x js-menu-close" role="button" aria-label="Close"></span>
      </div>

      <div class="select-menu-filters">
        <div class="select-menu-text-filter">
          <input type="text" aria-label="Filter branches/tags" id="context-commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Filter branches/tags">
        </div>
        <div class="select-menu-tabs">
          <ul>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="branches" data-filter-placeholder="Filter branches/tags" class="js-select-menu-tab">Branches</a>
            </li>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="tags" data-filter-placeholder="Find a tag…" class="js-select-menu-tab">Tags</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="branches">

        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item selected">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/gwenn/sqlite-dialect/blob/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java"
                 data-name="master"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="master">master</a>
            </div>
        </div>

          <div class="select-menu-no-results">Nothing to show</div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="tags">
        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


        </div>

        <div class="select-menu-no-results">Nothing to show</div>
      </div>

    </div>
  </div>
</div>

  <div class="button-group right">
    <a href="/gwenn/sqlite-dialect/find/master"
          class="js-show-file-finder minibutton empty-icon tooltipped tooltipped-s"
          data-pjax
          data-hotkey="t"
          aria-label="Quickly jump between files">
      <span class="octicon octicon-list-unordered"></span>
    </a>
    <button aria-label="Copy file path to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
  </div>

  <div class="breadcrumb js-zeroclipboard-target">
    <span class='repo-root js-repo-root'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">sqlite-dialect</span></a></span></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">src</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src/main" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">main</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src/main/java" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">java</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src/main/java/org" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">org</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src/main/java/org/hibernate" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">hibernate</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/gwenn/sqlite-dialect/tree/master/src/main/java/org/hibernate/dialect" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">dialect</span></a></span><span class="separator">/</span><strong class="final-path">SQLiteDialect.java</strong>
  </div>
</div>


  <div class="commit file-history-tease">
    <div class="file-history-tease-header">
        <img alt="gwenn" class="avatar" data-user="45554" height="24" src="https://avatars0.githubusercontent.com/u/45554?v=3&amp;s=48" width="24" />
        <span class="author"><a href="/gwenn" rel="author">gwenn</a></span>
        <time datetime="2013-12-27T19:17:14Z" is="relative-time">Dec 27, 2013</time>
        <div class="commit-title">
            <a href="/gwenn/sqlite-dialect/commit/c165e9273d4bb606b223e215072d460df3c1f81a" class="message" data-pjax="true" title="Update dialect with a custom SQLExceptionConverter.">Update dialect with a custom SQLExceptionConverter.</a>
        </div>
    </div>

    <div class="participation">
      <p class="quickstat">
        <a href="#blob_contributors_box" rel="facebox">
          <strong>1</strong>
           contributor
        </a>
      </p>
      
    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2 class="facebox-header">Users who have contributed to this file</h2>
      <ul class="facebox-user-list">
          <li class="facebox-user-list-item">
            <img alt="gwenn" data-user="45554" height="24" src="https://avatars0.githubusercontent.com/u/45554?v=3&amp;s=48" width="24" />
            <a href="/gwenn">gwenn</a>
          </li>
      </ul>
    </div>
  </div>

<div class="file-box">
  <div class="file">
    <div class="meta clearfix">
      <div class="info file-name">
          <span>288 lines (246 sloc)</span>
          <span class="meta-divider"></span>
        <span>9.245 kb</span>
      </div>
      <div class="actions">
        <div class="button-group">
          <a href="/gwenn/sqlite-dialect/raw/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java" class="minibutton " id="raw-url">Raw</a>
            <a href="/gwenn/sqlite-dialect/blame/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java" class="minibutton js-update-url-with-hash">Blame</a>
          <a href="/gwenn/sqlite-dialect/commits/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java" class="minibutton " rel="nofollow">History</a>
        </div><!-- /.button-group -->

          <a class="octicon-button tooltipped tooltipped-nw js-conduit-openfile-check"
             href="http://mac.github.com"
             data-url="github-mac://openRepo/https://github.com/gwenn/sqlite-dialect?branch=master&amp;filepath=src%2Fmain%2Fjava%2Forg%2Fhibernate%2Fdialect%2FSQLiteDialect.java"
             aria-label="Open this file in GitHub for Mac"
             data-failed-title="Your version of GitHub for Mac is too old to open this file. Try checking for updates.">
              <span class="octicon octicon-device-desktop"></span>
          </a>

              <a class="octicon-button tooltipped tooltipped-n js-update-url-with-hash"
                 aria-label="Clicking this button will fork this project so you can edit the file"
                 href="/gwenn/sqlite-dialect/edit/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java"
                 data-method="post" rel="nofollow"><span class="octicon octicon-pencil"></span></a>

            <a class="octicon-button danger tooltipped tooltipped-s"
               href="/gwenn/sqlite-dialect/delete/master/src/main/java/org/hibernate/dialect/SQLiteDialect.java"
               aria-label="Fork this project and delete file"
               data-method="post" data-test-id="delete-blob-file" rel="nofollow">
          <span class="octicon octicon-trashcan"></span>
        </a>
      </div><!-- /.actions -->
    </div>
    

  <div class="blob-wrapper data type-java">
      <table class="highlight tab-size-8 js-file-line-container">
      <tr>
        <td id="L1" class="blob-num js-line-number" data-line-number="1"></td>
        <td id="LC1" class="blob-code js-file-line"><span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L2" class="blob-num js-line-number" data-line-number="2"></td>
        <td id="LC2" class="blob-code js-file-line"><span class="pl-c"> * The author disclaims copyright to this source code.  In place of</span></td>
      </tr>
      <tr>
        <td id="L3" class="blob-num js-line-number" data-line-number="3"></td>
        <td id="LC3" class="blob-code js-file-line"><span class="pl-c"> * a legal notice, here is a blessing:</span></td>
      </tr>
      <tr>
        <td id="L4" class="blob-num js-line-number" data-line-number="4"></td>
        <td id="LC4" class="blob-code js-file-line"><span class="pl-c"> *</span></td>
      </tr>
      <tr>
        <td id="L5" class="blob-num js-line-number" data-line-number="5"></td>
        <td id="LC5" class="blob-code js-file-line"><span class="pl-c"> *    May you do good and not evil.</span></td>
      </tr>
      <tr>
        <td id="L6" class="blob-num js-line-number" data-line-number="6"></td>
        <td id="LC6" class="blob-code js-file-line"><span class="pl-c"> *    May you find forgiveness for yourself and forgive others.</span></td>
      </tr>
      <tr>
        <td id="L7" class="blob-num js-line-number" data-line-number="7"></td>
        <td id="LC7" class="blob-code js-file-line"><span class="pl-c"> *    May you share freely, never taking more than you give.</span></td>
      </tr>
      <tr>
        <td id="L8" class="blob-num js-line-number" data-line-number="8"></td>
        <td id="LC8" class="blob-code js-file-line"><span class="pl-c"> *</span></td>
      </tr>
      <tr>
        <td id="L9" class="blob-num js-line-number" data-line-number="9"></td>
        <td id="LC9" class="blob-code js-file-line"><span class="pl-c"> */</span></td>
      </tr>
      <tr>
        <td id="L10" class="blob-num js-line-number" data-line-number="10"></td>
        <td id="LC10" class="blob-code js-file-line"><span class="pl-k">package</span> <span class="pl-smp">org.hibernate.dialect</span>;</td>
      </tr>
      <tr>
        <td id="L11" class="blob-num js-line-number" data-line-number="11"></td>
        <td id="LC11" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L12" class="blob-num js-line-number" data-line-number="12"></td>
        <td id="LC12" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">java.sql.SQLException</span>;</td>
      </tr>
      <tr>
        <td id="L13" class="blob-num js-line-number" data-line-number="13"></td>
        <td id="LC13" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">java.sql.Types</span>;</td>
      </tr>
      <tr>
        <td id="L14" class="blob-num js-line-number" data-line-number="14"></td>
        <td id="LC14" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L15" class="blob-num js-line-number" data-line-number="15"></td>
        <td id="LC15" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.JDBCException</span>;</td>
      </tr>
      <tr>
        <td id="L16" class="blob-num js-line-number" data-line-number="16"></td>
        <td id="LC16" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.AbstractAnsiTrimEmulationFunction</span>;</td>
      </tr>
      <tr>
        <td id="L17" class="blob-num js-line-number" data-line-number="17"></td>
        <td id="LC17" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.NoArgSQLFunction</span>;</td>
      </tr>
      <tr>
        <td id="L18" class="blob-num js-line-number" data-line-number="18"></td>
        <td id="LC18" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.SQLFunction</span>;</td>
      </tr>
      <tr>
        <td id="L19" class="blob-num js-line-number" data-line-number="19"></td>
        <td id="LC19" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.SQLFunctionTemplate</span>;</td>
      </tr>
      <tr>
        <td id="L20" class="blob-num js-line-number" data-line-number="20"></td>
        <td id="LC20" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.StandardSQLFunction</span>;</td>
      </tr>
      <tr>
        <td id="L21" class="blob-num js-line-number" data-line-number="21"></td>
        <td id="LC21" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.dialect.function.VarArgsSQLFunction</span>;</td>
      </tr>
      <tr>
        <td id="L22" class="blob-num js-line-number" data-line-number="22"></td>
        <td id="LC22" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.exception.*</span>;</td>
      </tr>
      <tr>
        <td id="L23" class="blob-num js-line-number" data-line-number="23"></td>
        <td id="LC23" class="blob-code js-file-line"><span class="pl-k">import</span> <span class="pl-smi">org.hibernate.type.StandardBasicTypes</span>;</td>
      </tr>
      <tr>
        <td id="L24" class="blob-num js-line-number" data-line-number="24"></td>
        <td id="LC24" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L25" class="blob-num js-line-number" data-line-number="25"></td>
        <td id="LC25" class="blob-code js-file-line"><span class="pl-s">public</span> <span class="pl-s">class</span> <span class="pl-en">SQLiteDialect</span> <span class="pl-s">extends</span> <span class="pl-e">Dialect</span> {</td>
      </tr>
      <tr>
        <td id="L26" class="blob-num js-line-number" data-line-number="26"></td>
        <td id="LC26" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-en">SQLiteDialect</span>() {</td>
      </tr>
      <tr>
        <td id="L27" class="blob-num js-line-number" data-line-number="27"></td>
        <td id="LC27" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>BIT</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>boolean<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L28" class="blob-num js-line-number" data-line-number="28"></td>
        <td id="LC28" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>TINYINT</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>tinyint<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L29" class="blob-num js-line-number" data-line-number="29"></td>
        <td id="LC29" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>SMALLINT</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>smallint<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L30" class="blob-num js-line-number" data-line-number="30"></td>
        <td id="LC30" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>INTEGER</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>integer<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L31" class="blob-num js-line-number" data-line-number="31"></td>
        <td id="LC31" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>BIGINT</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>bigint<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L32" class="blob-num js-line-number" data-line-number="32"></td>
        <td id="LC32" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>FLOAT</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>float<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L33" class="blob-num js-line-number" data-line-number="33"></td>
        <td id="LC33" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>REAL</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>real<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L34" class="blob-num js-line-number" data-line-number="34"></td>
        <td id="LC34" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>DOUBLE</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>double<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L35" class="blob-num js-line-number" data-line-number="35"></td>
        <td id="LC35" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>NUMERIC</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>numeric($p, $s)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L36" class="blob-num js-line-number" data-line-number="36"></td>
        <td id="LC36" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>DECIMAL</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>decimal<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L37" class="blob-num js-line-number" data-line-number="37"></td>
        <td id="LC37" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>CHAR</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>char<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L38" class="blob-num js-line-number" data-line-number="38"></td>
        <td id="LC38" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>VARCHAR</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>varchar($l)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L39" class="blob-num js-line-number" data-line-number="39"></td>
        <td id="LC39" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>LONGVARCHAR</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>longvarchar<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L40" class="blob-num js-line-number" data-line-number="40"></td>
        <td id="LC40" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>DATE</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>date<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L41" class="blob-num js-line-number" data-line-number="41"></td>
        <td id="LC41" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>TIME</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>time<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L42" class="blob-num js-line-number" data-line-number="42"></td>
        <td id="LC42" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>TIMESTAMP</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>datetime<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L43" class="blob-num js-line-number" data-line-number="43"></td>
        <td id="LC43" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>BINARY</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>blob<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L44" class="blob-num js-line-number" data-line-number="44"></td>
        <td id="LC44" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>VARBINARY</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>blob<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L45" class="blob-num js-line-number" data-line-number="45"></td>
        <td id="LC45" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>LONGVARBINARY</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>blob<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L46" class="blob-num js-line-number" data-line-number="46"></td>
        <td id="LC46" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>BLOB</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>blob<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L47" class="blob-num js-line-number" data-line-number="47"></td>
        <td id="LC47" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>CLOB</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>clob<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L48" class="blob-num js-line-number" data-line-number="48"></td>
        <td id="LC48" class="blob-code js-file-line">    registerColumnType(<span class="pl-stj">Types</span><span class="pl-c1"><span class="pl-k">.</span>BOOLEAN</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>boolean<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L49" class="blob-num js-line-number" data-line-number="49"></td>
        <td id="LC49" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L50" class="blob-num js-line-number" data-line-number="50"></td>
        <td id="LC50" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>concat<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">VarArgsSQLFunction</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span><span class="pl-pds">&quot;</span></span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>||<span class="pl-pds">&quot;</span></span>, <span class="pl-s1"><span class="pl-pds">&quot;</span><span class="pl-pds">&quot;</span></span>) );</td>
      </tr>
      <tr>
        <td id="L51" class="blob-num js-line-number" data-line-number="51"></td>
        <td id="LC51" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>mod<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>INTEGER</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>?1 % ?2<span class="pl-pds">&quot;</span></span> ) );</td>
      </tr>
      <tr>
        <td id="L52" class="blob-num js-line-number" data-line-number="52"></td>
        <td id="LC52" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>quote<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">StandardSQLFunction</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>quote<span class="pl-pds">&quot;</span></span>, <span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>) );</td>
      </tr>
      <tr>
        <td id="L53" class="blob-num js-line-number" data-line-number="53"></td>
        <td id="LC53" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>random<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">NoArgSQLFunction</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>random<span class="pl-pds">&quot;</span></span>, <span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>INTEGER</span>) );</td>
      </tr>
      <tr>
        <td id="L54" class="blob-num js-line-number" data-line-number="54"></td>
        <td id="LC54" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>round<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">StandardSQLFunction</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>round<span class="pl-pds">&quot;</span></span>) );</td>
      </tr>
      <tr>
        <td id="L55" class="blob-num js-line-number" data-line-number="55"></td>
        <td id="LC55" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>substr<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">StandardSQLFunction</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>substr<span class="pl-pds">&quot;</span></span>, <span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>) );</td>
      </tr>
      <tr>
        <td id="L56" class="blob-num js-line-number" data-line-number="56"></td>
        <td id="LC56" class="blob-code js-file-line">    registerFunction( <span class="pl-s1"><span class="pl-pds">&quot;</span>trim<span class="pl-pds">&quot;</span></span>, <span class="pl-k">new</span> <span class="pl-stj">AbstractAnsiTrimEmulationFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L57" class="blob-num js-line-number" data-line-number="57"></td>
        <td id="LC57" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveBothSpaceTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L58" class="blob-num js-line-number" data-line-number="58"></td>
        <td id="LC58" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>trim(?1)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L59" class="blob-num js-line-number" data-line-number="59"></td>
        <td id="LC59" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L60" class="blob-num js-line-number" data-line-number="60"></td>
        <td id="LC60" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L61" class="blob-num js-line-number" data-line-number="61"></td>
        <td id="LC61" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveBothSpaceTrimFromFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L62" class="blob-num js-line-number" data-line-number="62"></td>
        <td id="LC62" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>trim(?2)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L63" class="blob-num js-line-number" data-line-number="63"></td>
        <td id="LC63" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L64" class="blob-num js-line-number" data-line-number="64"></td>
        <td id="LC64" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L65" class="blob-num js-line-number" data-line-number="65"></td>
        <td id="LC65" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveLeadingSpaceTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L66" class="blob-num js-line-number" data-line-number="66"></td>
        <td id="LC66" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>ltrim(?1)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L67" class="blob-num js-line-number" data-line-number="67"></td>
        <td id="LC67" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L68" class="blob-num js-line-number" data-line-number="68"></td>
        <td id="LC68" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L69" class="blob-num js-line-number" data-line-number="69"></td>
        <td id="LC69" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveTrailingSpaceTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L70" class="blob-num js-line-number" data-line-number="70"></td>
        <td id="LC70" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>rtrim(?1)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L71" class="blob-num js-line-number" data-line-number="71"></td>
        <td id="LC71" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L72" class="blob-num js-line-number" data-line-number="72"></td>
        <td id="LC72" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L73" class="blob-num js-line-number" data-line-number="73"></td>
        <td id="LC73" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveBothTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L74" class="blob-num js-line-number" data-line-number="74"></td>
        <td id="LC74" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>trim(?1, ?2)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L75" class="blob-num js-line-number" data-line-number="75"></td>
        <td id="LC75" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L76" class="blob-num js-line-number" data-line-number="76"></td>
        <td id="LC76" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L77" class="blob-num js-line-number" data-line-number="77"></td>
        <td id="LC77" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveLeadingTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L78" class="blob-num js-line-number" data-line-number="78"></td>
        <td id="LC78" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>ltrim(?1, ?2)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L79" class="blob-num js-line-number" data-line-number="79"></td>
        <td id="LC79" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L80" class="blob-num js-line-number" data-line-number="80"></td>
        <td id="LC80" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L81" class="blob-num js-line-number" data-line-number="81"></td>
        <td id="LC81" class="blob-code js-file-line">        <span class="pl-s">protected</span> <span class="pl-stj">SQLFunction</span> <span class="pl-en">resolveTrailingTrimFunction</span>() {</td>
      </tr>
      <tr>
        <td id="L82" class="blob-num js-line-number" data-line-number="82"></td>
        <td id="LC82" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLFunctionTemplate</span>(<span class="pl-stj">StandardBasicTypes</span><span class="pl-c1"><span class="pl-k">.</span>STRING</span>, <span class="pl-s1"><span class="pl-pds">&quot;</span>rtrim(?1, ?2)<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L83" class="blob-num js-line-number" data-line-number="83"></td>
        <td id="LC83" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L84" class="blob-num js-line-number" data-line-number="84"></td>
        <td id="LC84" class="blob-code js-file-line">    } );</td>
      </tr>
      <tr>
        <td id="L85" class="blob-num js-line-number" data-line-number="85"></td>
        <td id="LC85" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L86" class="blob-num js-line-number" data-line-number="86"></td>
        <td id="LC86" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L87" class="blob-num js-line-number" data-line-number="87"></td>
        <td id="LC87" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L88" class="blob-num js-line-number" data-line-number="88"></td>
        <td id="LC88" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsIdentityColumns</span>() {</td>
      </tr>
      <tr>
        <td id="L89" class="blob-num js-line-number" data-line-number="89"></td>
        <td id="LC89" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L90" class="blob-num js-line-number" data-line-number="90"></td>
        <td id="LC90" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L91" class="blob-num js-line-number" data-line-number="91"></td>
        <td id="LC91" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L92" class="blob-num js-line-number" data-line-number="92"></td>
        <td id="LC92" class="blob-code js-file-line">  <span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L93" class="blob-num js-line-number" data-line-number="93"></td>
        <td id="LC93" class="blob-code js-file-line"><span class="pl-c">  public boolean supportsInsertSelectIdentity() {</span></td>
      </tr>
      <tr>
        <td id="L94" class="blob-num js-line-number" data-line-number="94"></td>
        <td id="LC94" class="blob-code js-file-line"><span class="pl-c">    return true; // As specified in NHibernate dialect</span></td>
      </tr>
      <tr>
        <td id="L95" class="blob-num js-line-number" data-line-number="95"></td>
        <td id="LC95" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L96" class="blob-num js-line-number" data-line-number="96"></td>
        <td id="LC96" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L97" class="blob-num js-line-number" data-line-number="97"></td>
        <td id="LC97" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L98" class="blob-num js-line-number" data-line-number="98"></td>
        <td id="LC98" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L99" class="blob-num js-line-number" data-line-number="99"></td>
        <td id="LC99" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">hasDataTypeInIdentityColumn</span>() {</td>
      </tr>
      <tr>
        <td id="L100" class="blob-num js-line-number" data-line-number="100"></td>
        <td id="LC100" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>; <span class="pl-c">// As specified in NHibernate dialect</span></td>
      </tr>
      <tr>
        <td id="L101" class="blob-num js-line-number" data-line-number="101"></td>
        <td id="LC101" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L102" class="blob-num js-line-number" data-line-number="102"></td>
        <td id="LC102" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L103" class="blob-num js-line-number" data-line-number="103"></td>
        <td id="LC103" class="blob-code js-file-line">  <span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L104" class="blob-num js-line-number" data-line-number="104"></td>
        <td id="LC104" class="blob-code js-file-line"><span class="pl-c">  public String appendIdentitySelectToInsert(String insertString) {</span></td>
      </tr>
      <tr>
        <td id="L105" class="blob-num js-line-number" data-line-number="105"></td>
        <td id="LC105" class="blob-code js-file-line"><span class="pl-c">    return new StringBuffer(insertString.length()+30). // As specified in NHibernate dialect</span></td>
      </tr>
      <tr>
        <td id="L106" class="blob-num js-line-number" data-line-number="106"></td>
        <td id="LC106" class="blob-code js-file-line"><span class="pl-c">      append(insertString).</span></td>
      </tr>
      <tr>
        <td id="L107" class="blob-num js-line-number" data-line-number="107"></td>
        <td id="LC107" class="blob-code js-file-line"><span class="pl-c">      append(&quot;; &quot;).append(getIdentitySelectString()).</span></td>
      </tr>
      <tr>
        <td id="L108" class="blob-num js-line-number" data-line-number="108"></td>
        <td id="LC108" class="blob-code js-file-line"><span class="pl-c">      toString();</span></td>
      </tr>
      <tr>
        <td id="L109" class="blob-num js-line-number" data-line-number="109"></td>
        <td id="LC109" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L110" class="blob-num js-line-number" data-line-number="110"></td>
        <td id="LC110" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L111" class="blob-num js-line-number" data-line-number="111"></td>
        <td id="LC111" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L112" class="blob-num js-line-number" data-line-number="112"></td>
        <td id="LC112" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L113" class="blob-num js-line-number" data-line-number="113"></td>
        <td id="LC113" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getIdentityColumnString</span>() {</td>
      </tr>
      <tr>
        <td id="L114" class="blob-num js-line-number" data-line-number="114"></td>
        <td id="LC114" class="blob-code js-file-line">    <span class="pl-c">// return &quot;integer primary key autoincrement&quot;;</span></td>
      </tr>
      <tr>
        <td id="L115" class="blob-num js-line-number" data-line-number="115"></td>
        <td id="LC115" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span>integer<span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L116" class="blob-num js-line-number" data-line-number="116"></td>
        <td id="LC116" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L117" class="blob-num js-line-number" data-line-number="117"></td>
        <td id="LC117" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L118" class="blob-num js-line-number" data-line-number="118"></td>
        <td id="LC118" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L119" class="blob-num js-line-number" data-line-number="119"></td>
        <td id="LC119" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getIdentitySelectString</span>() {</td>
      </tr>
      <tr>
        <td id="L120" class="blob-num js-line-number" data-line-number="120"></td>
        <td id="LC120" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span>select last_insert_rowid()<span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L121" class="blob-num js-line-number" data-line-number="121"></td>
        <td id="LC121" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L122" class="blob-num js-line-number" data-line-number="122"></td>
        <td id="LC122" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L123" class="blob-num js-line-number" data-line-number="123"></td>
        <td id="LC123" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L124" class="blob-num js-line-number" data-line-number="124"></td>
        <td id="LC124" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsLimit</span>() {</td>
      </tr>
      <tr>
        <td id="L125" class="blob-num js-line-number" data-line-number="125"></td>
        <td id="LC125" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L126" class="blob-num js-line-number" data-line-number="126"></td>
        <td id="LC126" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L127" class="blob-num js-line-number" data-line-number="127"></td>
        <td id="LC127" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L128" class="blob-num js-line-number" data-line-number="128"></td>
        <td id="LC128" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L129" class="blob-num js-line-number" data-line-number="129"></td>
        <td id="LC129" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">bindLimitParametersInReverseOrder</span>() {</td>
      </tr>
      <tr>
        <td id="L130" class="blob-num js-line-number" data-line-number="130"></td>
        <td id="LC130" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L131" class="blob-num js-line-number" data-line-number="131"></td>
        <td id="LC131" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L132" class="blob-num js-line-number" data-line-number="132"></td>
        <td id="LC132" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L133" class="blob-num js-line-number" data-line-number="133"></td>
        <td id="LC133" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L134" class="blob-num js-line-number" data-line-number="134"></td>
        <td id="LC134" class="blob-code js-file-line">  <span class="pl-s">protected</span> <span class="pl-stj">String</span> <span class="pl-en">getLimitString</span>(<span class="pl-stj">String</span> <span class="pl-v">query</span>, <span class="pl-st">boolean</span> <span class="pl-v">hasOffset</span>) {</td>
      </tr>
      <tr>
        <td id="L135" class="blob-num js-line-number" data-line-number="135"></td>
        <td id="LC135" class="blob-code js-file-line">    <span class="pl-k">return</span> query <span class="pl-k">+</span> (hasOffset <span class="pl-k">?</span> <span class="pl-s1"><span class="pl-pds">&quot;</span> limit ? offset ?<span class="pl-pds">&quot;</span></span> <span class="pl-k">:</span> <span class="pl-s1"><span class="pl-pds">&quot;</span> limit ?<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L136" class="blob-num js-line-number" data-line-number="136"></td>
        <td id="LC136" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L137" class="blob-num js-line-number" data-line-number="137"></td>
        <td id="LC137" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L138" class="blob-num js-line-number" data-line-number="138"></td>
        <td id="LC138" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L139" class="blob-num js-line-number" data-line-number="139"></td>
        <td id="LC139" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsTemporaryTables</span>() {</td>
      </tr>
      <tr>
        <td id="L140" class="blob-num js-line-number" data-line-number="140"></td>
        <td id="LC140" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L141" class="blob-num js-line-number" data-line-number="141"></td>
        <td id="LC141" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L142" class="blob-num js-line-number" data-line-number="142"></td>
        <td id="LC142" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L143" class="blob-num js-line-number" data-line-number="143"></td>
        <td id="LC143" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L144" class="blob-num js-line-number" data-line-number="144"></td>
        <td id="LC144" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getCreateTemporaryTableString</span>() {</td>
      </tr>
      <tr>
        <td id="L145" class="blob-num js-line-number" data-line-number="145"></td>
        <td id="LC145" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span>create temporary table if not exists<span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L146" class="blob-num js-line-number" data-line-number="146"></td>
        <td id="LC146" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L147" class="blob-num js-line-number" data-line-number="147"></td>
        <td id="LC147" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L148" class="blob-num js-line-number" data-line-number="148"></td>
        <td id="LC148" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L149" class="blob-num js-line-number" data-line-number="149"></td>
        <td id="LC149" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">Boolean</span> <span class="pl-en">performTemporaryTableDDLInIsolation</span>() {</td>
      </tr>
      <tr>
        <td id="L150" class="blob-num js-line-number" data-line-number="150"></td>
        <td id="LC150" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-stj">Boolean</span><span class="pl-c1"><span class="pl-k">.</span>FALSE</span>;</td>
      </tr>
      <tr>
        <td id="L151" class="blob-num js-line-number" data-line-number="151"></td>
        <td id="LC151" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L152" class="blob-num js-line-number" data-line-number="152"></td>
        <td id="LC152" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L153" class="blob-num js-line-number" data-line-number="153"></td>
        <td id="LC153" class="blob-code js-file-line">  <span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L154" class="blob-num js-line-number" data-line-number="154"></td>
        <td id="LC154" class="blob-code js-file-line"><span class="pl-c">  @Override</span></td>
      </tr>
      <tr>
        <td id="L155" class="blob-num js-line-number" data-line-number="155"></td>
        <td id="LC155" class="blob-code js-file-line"><span class="pl-c">  public boolean dropTemporaryTableAfterUse() {</span></td>
      </tr>
      <tr>
        <td id="L156" class="blob-num js-line-number" data-line-number="156"></td>
        <td id="LC156" class="blob-code js-file-line"><span class="pl-c">    return true; // temporary tables are only dropped when the connection is closed. If the connection is pooled...</span></td>
      </tr>
      <tr>
        <td id="L157" class="blob-num js-line-number" data-line-number="157"></td>
        <td id="LC157" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L158" class="blob-num js-line-number" data-line-number="158"></td>
        <td id="LC158" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L159" class="blob-num js-line-number" data-line-number="159"></td>
        <td id="LC159" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L160" class="blob-num js-line-number" data-line-number="160"></td>
        <td id="LC160" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L161" class="blob-num js-line-number" data-line-number="161"></td>
        <td id="LC161" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsCurrentTimestampSelection</span>() {</td>
      </tr>
      <tr>
        <td id="L162" class="blob-num js-line-number" data-line-number="162"></td>
        <td id="LC162" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L163" class="blob-num js-line-number" data-line-number="163"></td>
        <td id="LC163" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L164" class="blob-num js-line-number" data-line-number="164"></td>
        <td id="LC164" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L165" class="blob-num js-line-number" data-line-number="165"></td>
        <td id="LC165" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L166" class="blob-num js-line-number" data-line-number="166"></td>
        <td id="LC166" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">isCurrentTimestampSelectStringCallable</span>() {</td>
      </tr>
      <tr>
        <td id="L167" class="blob-num js-line-number" data-line-number="167"></td>
        <td id="LC167" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>;</td>
      </tr>
      <tr>
        <td id="L168" class="blob-num js-line-number" data-line-number="168"></td>
        <td id="LC168" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L169" class="blob-num js-line-number" data-line-number="169"></td>
        <td id="LC169" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L170" class="blob-num js-line-number" data-line-number="170"></td>
        <td id="LC170" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L171" class="blob-num js-line-number" data-line-number="171"></td>
        <td id="LC171" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getCurrentTimestampSelectString</span>() {</td>
      </tr>
      <tr>
        <td id="L172" class="blob-num js-line-number" data-line-number="172"></td>
        <td id="LC172" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span>select current_timestamp<span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L173" class="blob-num js-line-number" data-line-number="173"></td>
        <td id="LC173" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L174" class="blob-num js-line-number" data-line-number="174"></td>
        <td id="LC174" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L175" class="blob-num js-line-number" data-line-number="175"></td>
        <td id="LC175" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_BUSY</span> <span class="pl-k">=</span> <span class="pl-c1">5</span>;</td>
      </tr>
      <tr>
        <td id="L176" class="blob-num js-line-number" data-line-number="176"></td>
        <td id="LC176" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_LOCKED</span> <span class="pl-k">=</span> <span class="pl-c1">6</span>;</td>
      </tr>
      <tr>
        <td id="L177" class="blob-num js-line-number" data-line-number="177"></td>
        <td id="LC177" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_IOERR</span> <span class="pl-k">=</span> <span class="pl-c1">10</span>;</td>
      </tr>
      <tr>
        <td id="L178" class="blob-num js-line-number" data-line-number="178"></td>
        <td id="LC178" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_CORRUPT</span> <span class="pl-k">=</span> <span class="pl-c1">11</span>;</td>
      </tr>
      <tr>
        <td id="L179" class="blob-num js-line-number" data-line-number="179"></td>
        <td id="LC179" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_NOTFOUND</span> <span class="pl-k">=</span> <span class="pl-c1">12</span>;</td>
      </tr>
      <tr>
        <td id="L180" class="blob-num js-line-number" data-line-number="180"></td>
        <td id="LC180" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_FULL</span> <span class="pl-k">=</span> <span class="pl-c1">13</span>;</td>
      </tr>
      <tr>
        <td id="L181" class="blob-num js-line-number" data-line-number="181"></td>
        <td id="LC181" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_CANTOPEN</span> <span class="pl-k">=</span> <span class="pl-c1">14</span>;</td>
      </tr>
      <tr>
        <td id="L182" class="blob-num js-line-number" data-line-number="182"></td>
        <td id="LC182" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_PROTOCOL</span> <span class="pl-k">=</span> <span class="pl-c1">15</span>;</td>
      </tr>
      <tr>
        <td id="L183" class="blob-num js-line-number" data-line-number="183"></td>
        <td id="LC183" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_TOOBIG</span> <span class="pl-k">=</span> <span class="pl-c1">18</span>;</td>
      </tr>
      <tr>
        <td id="L184" class="blob-num js-line-number" data-line-number="184"></td>
        <td id="LC184" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_CONSTRAINT</span> <span class="pl-k">=</span> <span class="pl-c1">19</span>;</td>
      </tr>
      <tr>
        <td id="L185" class="blob-num js-line-number" data-line-number="185"></td>
        <td id="LC185" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_MISMATCH</span> <span class="pl-k">=</span> <span class="pl-c1">20</span>;</td>
      </tr>
      <tr>
        <td id="L186" class="blob-num js-line-number" data-line-number="186"></td>
        <td id="LC186" class="blob-code js-file-line">  <span class="pl-s">private</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-st">int</span> <span class="pl-c1">SQLITE_NOTADB</span> <span class="pl-k">=</span> <span class="pl-c1">26</span>;</td>
      </tr>
      <tr>
        <td id="L187" class="blob-num js-line-number" data-line-number="187"></td>
        <td id="LC187" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L188" class="blob-num js-line-number" data-line-number="188"></td>
        <td id="LC188" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">SQLExceptionConverter</span> <span class="pl-en">buildSQLExceptionConverter</span>() {</td>
      </tr>
      <tr>
        <td id="L189" class="blob-num js-line-number" data-line-number="189"></td>
        <td id="LC189" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">SQLExceptionConverter</span>() {</td>
      </tr>
      <tr>
        <td id="L190" class="blob-num js-line-number" data-line-number="190"></td>
        <td id="LC190" class="blob-code js-file-line">      <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L191" class="blob-num js-line-number" data-line-number="191"></td>
        <td id="LC191" class="blob-code js-file-line">      <span class="pl-s">public</span> <span class="pl-stj">JDBCException</span> <span class="pl-en">convert</span>(<span class="pl-stj">SQLException</span> <span class="pl-v">sqlException</span>, <span class="pl-stj">String</span> <span class="pl-v">message</span>, <span class="pl-stj">String</span> <span class="pl-v">sql</span>) {</td>
      </tr>
      <tr>
        <td id="L192" class="blob-num js-line-number" data-line-number="192"></td>
        <td id="LC192" class="blob-code js-file-line">        <span class="pl-s">final</span> <span class="pl-st">int</span> errorCode <span class="pl-k">=</span> <span class="pl-stj">JDBCExceptionHelper</span><span class="pl-k">.</span>extractErrorCode(sqlException);</td>
      </tr>
      <tr>
        <td id="L193" class="blob-num js-line-number" data-line-number="193"></td>
        <td id="LC193" class="blob-code js-file-line">        <span class="pl-k">if</span> (errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_CONSTRAINT</span>) {</td>
      </tr>
      <tr>
        <td id="L194" class="blob-num js-line-number" data-line-number="194"></td>
        <td id="LC194" class="blob-code js-file-line">          <span class="pl-s">final</span> <span class="pl-stj">String</span> constraintName <span class="pl-k">=</span> <span class="pl-c1">EXTRACTER</span><span class="pl-k">.</span>extractConstraintName(sqlException);</td>
      </tr>
      <tr>
        <td id="L195" class="blob-num js-line-number" data-line-number="195"></td>
        <td id="LC195" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">ConstraintViolationException</span>(message, sqlException, sql, constraintName);</td>
      </tr>
      <tr>
        <td id="L196" class="blob-num js-line-number" data-line-number="196"></td>
        <td id="LC196" class="blob-code js-file-line">        } <span class="pl-k">else</span> <span class="pl-k">if</span> (errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_TOOBIG</span> <span class="pl-k">||</span> errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_MISMATCH</span>) {</td>
      </tr>
      <tr>
        <td id="L197" class="blob-num js-line-number" data-line-number="197"></td>
        <td id="LC197" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">DataException</span>(message, sqlException, sql);</td>
      </tr>
      <tr>
        <td id="L198" class="blob-num js-line-number" data-line-number="198"></td>
        <td id="LC198" class="blob-code js-file-line">        } <span class="pl-k">else</span> <span class="pl-k">if</span> (errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_BUSY</span> <span class="pl-k">||</span> errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_LOCKED</span>) {</td>
      </tr>
      <tr>
        <td id="L199" class="blob-num js-line-number" data-line-number="199"></td>
        <td id="LC199" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">LockAcquisitionException</span>(message, sqlException, sql);</td>
      </tr>
      <tr>
        <td id="L200" class="blob-num js-line-number" data-line-number="200"></td>
        <td id="LC200" class="blob-code js-file-line">        } <span class="pl-k">else</span> <span class="pl-k">if</span> ((errorCode <span class="pl-k">&gt;=</span> <span class="pl-c1">SQLITE_IOERR</span> <span class="pl-k">&amp;&amp;</span> errorCode <span class="pl-k">&lt;=</span> <span class="pl-c1">SQLITE_PROTOCOL</span>) <span class="pl-k">||</span> errorCode <span class="pl-k">==</span> <span class="pl-c1">SQLITE_NOTADB</span>) {</td>
      </tr>
      <tr>
        <td id="L201" class="blob-num js-line-number" data-line-number="201"></td>
        <td id="LC201" class="blob-code js-file-line">          <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">JDBCConnectionException</span>(message, sqlException, sql);</td>
      </tr>
      <tr>
        <td id="L202" class="blob-num js-line-number" data-line-number="202"></td>
        <td id="LC202" class="blob-code js-file-line">        }</td>
      </tr>
      <tr>
        <td id="L203" class="blob-num js-line-number" data-line-number="203"></td>
        <td id="LC203" class="blob-code js-file-line">        <span class="pl-k">return</span> <span class="pl-k">new</span> <span class="pl-stj">GenericJDBCException</span>(message, sqlException, sql);</td>
      </tr>
      <tr>
        <td id="L204" class="blob-num js-line-number" data-line-number="204"></td>
        <td id="LC204" class="blob-code js-file-line">      }</td>
      </tr>
      <tr>
        <td id="L205" class="blob-num js-line-number" data-line-number="205"></td>
        <td id="LC205" class="blob-code js-file-line">    };</td>
      </tr>
      <tr>
        <td id="L206" class="blob-num js-line-number" data-line-number="206"></td>
        <td id="LC206" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L207" class="blob-num js-line-number" data-line-number="207"></td>
        <td id="LC207" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L208" class="blob-num js-line-number" data-line-number="208"></td>
        <td id="LC208" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-s">static</span> <span class="pl-s">final</span> <span class="pl-stj">ViolatedConstraintNameExtracter</span> <span class="pl-c1">EXTRACTER</span> <span class="pl-k">=</span> <span class="pl-k">new</span> <span class="pl-stj">TemplatedViolatedConstraintNameExtracter</span>() {</td>
      </tr>
      <tr>
        <td id="L209" class="blob-num js-line-number" data-line-number="209"></td>
        <td id="LC209" class="blob-code js-file-line">    <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">extractConstraintName</span>(<span class="pl-stj">SQLException</span> <span class="pl-v">sqle</span>) {</td>
      </tr>
      <tr>
        <td id="L210" class="blob-num js-line-number" data-line-number="210"></td>
        <td id="LC210" class="blob-code js-file-line">      <span class="pl-k">return</span> extractUsingTemplate( <span class="pl-s1"><span class="pl-pds">&quot;</span>constraint <span class="pl-pds">&quot;</span></span>, <span class="pl-s1"><span class="pl-pds">&quot;</span> failed<span class="pl-pds">&quot;</span></span>, sqle<span class="pl-k">.</span>getMessage() );</td>
      </tr>
      <tr>
        <td id="L211" class="blob-num js-line-number" data-line-number="211"></td>
        <td id="LC211" class="blob-code js-file-line">    }</td>
      </tr>
      <tr>
        <td id="L212" class="blob-num js-line-number" data-line-number="212"></td>
        <td id="LC212" class="blob-code js-file-line">  };</td>
      </tr>
      <tr>
        <td id="L213" class="blob-num js-line-number" data-line-number="213"></td>
        <td id="LC213" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L214" class="blob-num js-line-number" data-line-number="214"></td>
        <td id="LC214" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L215" class="blob-num js-line-number" data-line-number="215"></td>
        <td id="LC215" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsUnionAll</span>() {</td>
      </tr>
      <tr>
        <td id="L216" class="blob-num js-line-number" data-line-number="216"></td>
        <td id="LC216" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L217" class="blob-num js-line-number" data-line-number="217"></td>
        <td id="LC217" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L218" class="blob-num js-line-number" data-line-number="218"></td>
        <td id="LC218" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L219" class="blob-num js-line-number" data-line-number="219"></td>
        <td id="LC219" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L220" class="blob-num js-line-number" data-line-number="220"></td>
        <td id="LC220" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">hasAlterTable</span>() {</td>
      </tr>
      <tr>
        <td id="L221" class="blob-num js-line-number" data-line-number="221"></td>
        <td id="LC221" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>; <span class="pl-c">// As specified in NHibernate dialect</span></td>
      </tr>
      <tr>
        <td id="L222" class="blob-num js-line-number" data-line-number="222"></td>
        <td id="LC222" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L223" class="blob-num js-line-number" data-line-number="223"></td>
        <td id="LC223" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L224" class="blob-num js-line-number" data-line-number="224"></td>
        <td id="LC224" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L225" class="blob-num js-line-number" data-line-number="225"></td>
        <td id="LC225" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">dropConstraints</span>() {</td>
      </tr>
      <tr>
        <td id="L226" class="blob-num js-line-number" data-line-number="226"></td>
        <td id="LC226" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>;</td>
      </tr>
      <tr>
        <td id="L227" class="blob-num js-line-number" data-line-number="227"></td>
        <td id="LC227" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L228" class="blob-num js-line-number" data-line-number="228"></td>
        <td id="LC228" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L229" class="blob-num js-line-number" data-line-number="229"></td>
        <td id="LC229" class="blob-code js-file-line">  <span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L230" class="blob-num js-line-number" data-line-number="230"></td>
        <td id="LC230" class="blob-code js-file-line"><span class="pl-c">  public String getAddColumnString() {</span></td>
      </tr>
      <tr>
        <td id="L231" class="blob-num js-line-number" data-line-number="231"></td>
        <td id="LC231" class="blob-code js-file-line"><span class="pl-c">    return &quot;add column&quot;;</span></td>
      </tr>
      <tr>
        <td id="L232" class="blob-num js-line-number" data-line-number="232"></td>
        <td id="LC232" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L233" class="blob-num js-line-number" data-line-number="233"></td>
        <td id="LC233" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L234" class="blob-num js-line-number" data-line-number="234"></td>
        <td id="LC234" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L235" class="blob-num js-line-number" data-line-number="235"></td>
        <td id="LC235" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L236" class="blob-num js-line-number" data-line-number="236"></td>
        <td id="LC236" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getForUpdateString</span>() {</td>
      </tr>
      <tr>
        <td id="L237" class="blob-num js-line-number" data-line-number="237"></td>
        <td id="LC237" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span><span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L238" class="blob-num js-line-number" data-line-number="238"></td>
        <td id="LC238" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L239" class="blob-num js-line-number" data-line-number="239"></td>
        <td id="LC239" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L240" class="blob-num js-line-number" data-line-number="240"></td>
        <td id="LC240" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L241" class="blob-num js-line-number" data-line-number="241"></td>
        <td id="LC241" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsOuterJoinForUpdate</span>() {</td>
      </tr>
      <tr>
        <td id="L242" class="blob-num js-line-number" data-line-number="242"></td>
        <td id="LC242" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>;</td>
      </tr>
      <tr>
        <td id="L243" class="blob-num js-line-number" data-line-number="243"></td>
        <td id="LC243" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L244" class="blob-num js-line-number" data-line-number="244"></td>
        <td id="LC244" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L245" class="blob-num js-line-number" data-line-number="245"></td>
        <td id="LC245" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L246" class="blob-num js-line-number" data-line-number="246"></td>
        <td id="LC246" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getDropForeignKeyString</span>() {</td>
      </tr>
      <tr>
        <td id="L247" class="blob-num js-line-number" data-line-number="247"></td>
        <td id="LC247" class="blob-code js-file-line">    <span class="pl-k">throw</span> <span class="pl-k">new</span> <span class="pl-stj">UnsupportedOperationException</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>No drop foreign key syntax supported by SQLiteDialect<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L248" class="blob-num js-line-number" data-line-number="248"></td>
        <td id="LC248" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L249" class="blob-num js-line-number" data-line-number="249"></td>
        <td id="LC249" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L250" class="blob-num js-line-number" data-line-number="250"></td>
        <td id="LC250" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L251" class="blob-num js-line-number" data-line-number="251"></td>
        <td id="LC251" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getAddForeignKeyConstraintString</span>(<span class="pl-stj">String</span> <span class="pl-v">constraintName</span>,</td>
      </tr>
      <tr>
        <td id="L252" class="blob-num js-line-number" data-line-number="252"></td>
        <td id="LC252" class="blob-code js-file-line">      <span class="pl-st">String</span>[] <span class="pl-v">foreignKey</span>, <span class="pl-stj">String</span> <span class="pl-v">referencedTable</span>, <span class="pl-st">String</span>[] <span class="pl-v">primaryKey</span>,</td>
      </tr>
      <tr>
        <td id="L253" class="blob-num js-line-number" data-line-number="253"></td>
        <td id="LC253" class="blob-code js-file-line">      <span class="pl-st">boolean</span> <span class="pl-v">referencesPrimaryKey</span>) {</td>
      </tr>
      <tr>
        <td id="L254" class="blob-num js-line-number" data-line-number="254"></td>
        <td id="LC254" class="blob-code js-file-line">    <span class="pl-k">throw</span> <span class="pl-k">new</span> <span class="pl-stj">UnsupportedOperationException</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>No add foreign key syntax supported by SQLiteDialect<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L255" class="blob-num js-line-number" data-line-number="255"></td>
        <td id="LC255" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L256" class="blob-num js-line-number" data-line-number="256"></td>
        <td id="LC256" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L257" class="blob-num js-line-number" data-line-number="257"></td>
        <td id="LC257" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L258" class="blob-num js-line-number" data-line-number="258"></td>
        <td id="LC258" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getAddPrimaryKeyConstraintString</span>(<span class="pl-stj">String</span> <span class="pl-v">constraintName</span>) {</td>
      </tr>
      <tr>
        <td id="L259" class="blob-num js-line-number" data-line-number="259"></td>
        <td id="LC259" class="blob-code js-file-line">    <span class="pl-k">throw</span> <span class="pl-k">new</span> <span class="pl-stj">UnsupportedOperationException</span>(<span class="pl-s1"><span class="pl-pds">&quot;</span>No add primary key syntax supported by SQLiteDialect<span class="pl-pds">&quot;</span></span>);</td>
      </tr>
      <tr>
        <td id="L260" class="blob-num js-line-number" data-line-number="260"></td>
        <td id="LC260" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L261" class="blob-num js-line-number" data-line-number="261"></td>
        <td id="LC261" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L262" class="blob-num js-line-number" data-line-number="262"></td>
        <td id="LC262" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L263" class="blob-num js-line-number" data-line-number="263"></td>
        <td id="LC263" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsIfExistsBeforeTableName</span>() {</td>
      </tr>
      <tr>
        <td id="L264" class="blob-num js-line-number" data-line-number="264"></td>
        <td id="LC264" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">true</span>;</td>
      </tr>
      <tr>
        <td id="L265" class="blob-num js-line-number" data-line-number="265"></td>
        <td id="LC265" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L266" class="blob-num js-line-number" data-line-number="266"></td>
        <td id="LC266" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L267" class="blob-num js-line-number" data-line-number="267"></td>
        <td id="LC267" class="blob-code js-file-line">  <span class="pl-c">/*</span></td>
      </tr>
      <tr>
        <td id="L268" class="blob-num js-line-number" data-line-number="268"></td>
        <td id="LC268" class="blob-code js-file-line"><span class="pl-c">  public boolean supportsCascadeDelete() {</span></td>
      </tr>
      <tr>
        <td id="L269" class="blob-num js-line-number" data-line-number="269"></td>
        <td id="LC269" class="blob-code js-file-line"><span class="pl-c">    return true;</span></td>
      </tr>
      <tr>
        <td id="L270" class="blob-num js-line-number" data-line-number="270"></td>
        <td id="LC270" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L271" class="blob-num js-line-number" data-line-number="271"></td>
        <td id="LC271" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L272" class="blob-num js-line-number" data-line-number="272"></td>
        <td id="LC272" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L273" class="blob-num js-line-number" data-line-number="273"></td>
        <td id="LC273" class="blob-code js-file-line">  <span class="pl-c">/* not case insensitive for unicode characters by default (ICU extension needed)</span></td>
      </tr>
      <tr>
        <td id="L274" class="blob-num js-line-number" data-line-number="274"></td>
        <td id="LC274" class="blob-code js-file-line"><span class="pl-c">  public boolean supportsCaseInsensitiveLike() {</span></td>
      </tr>
      <tr>
        <td id="L275" class="blob-num js-line-number" data-line-number="275"></td>
        <td id="LC275" class="blob-code js-file-line"><span class="pl-c">    return true;</span></td>
      </tr>
      <tr>
        <td id="L276" class="blob-num js-line-number" data-line-number="276"></td>
        <td id="LC276" class="blob-code js-file-line"><span class="pl-c">  }</span></td>
      </tr>
      <tr>
        <td id="L277" class="blob-num js-line-number" data-line-number="277"></td>
        <td id="LC277" class="blob-code js-file-line"><span class="pl-c">  */</span></td>
      </tr>
      <tr>
        <td id="L278" class="blob-num js-line-number" data-line-number="278"></td>
        <td id="LC278" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L279" class="blob-num js-line-number" data-line-number="279"></td>
        <td id="LC279" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L280" class="blob-num js-line-number" data-line-number="280"></td>
        <td id="LC280" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-st">boolean</span> <span class="pl-en">supportsTupleDistinctCounts</span>() {</td>
      </tr>
      <tr>
        <td id="L281" class="blob-num js-line-number" data-line-number="281"></td>
        <td id="LC281" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-c1">false</span>;</td>
      </tr>
      <tr>
        <td id="L282" class="blob-num js-line-number" data-line-number="282"></td>
        <td id="LC282" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L283" class="blob-num js-line-number" data-line-number="283"></td>
        <td id="LC283" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L284" class="blob-num js-line-number" data-line-number="284"></td>
        <td id="LC284" class="blob-code js-file-line">  <span class="pl-st">@Override</span></td>
      </tr>
      <tr>
        <td id="L285" class="blob-num js-line-number" data-line-number="285"></td>
        <td id="LC285" class="blob-code js-file-line">  <span class="pl-s">public</span> <span class="pl-stj">String</span> <span class="pl-en">getSelectGUIDString</span>() {</td>
      </tr>
      <tr>
        <td id="L286" class="blob-num js-line-number" data-line-number="286"></td>
        <td id="LC286" class="blob-code js-file-line">    <span class="pl-k">return</span> <span class="pl-s1"><span class="pl-pds">&quot;</span>select hex(randomblob(16))<span class="pl-pds">&quot;</span></span>;</td>
      </tr>
      <tr>
        <td id="L287" class="blob-num js-line-number" data-line-number="287"></td>
        <td id="LC287" class="blob-code js-file-line">  }</td>
      </tr>
      <tr>
        <td id="L288" class="blob-num js-line-number" data-line-number="288"></td>
        <td id="LC288" class="blob-code js-file-line">}</td>
      </tr>
</table>

  </div>

  </div>
</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" class="js-jump-to-line-form">
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="button">Go</button>
  </form>
</div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer" role="contentinfo">
    <ul class="site-footer-links right">
      <li><a href="https://status.github.com/">Status</a></li>
      <li><a href="https://developer.github.com">API</a></li>
      <li><a href="http://training.github.com">Training</a></li>
      <li><a href="http://shop.github.com">Shop</a></li>
      <li><a href="/blog">Blog</a></li>
      <li><a href="/about">About</a></li>

    </ul>

    <a href="/" aria-label="Homepage">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
    </a>

    <ul class="site-footer-links">
      <li>&copy; 2015 <span title="0.05707s from github-fe138-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="/site/terms">Terms</a></li>
        <li><a href="/site/privacy">Privacy</a></li>
        <li><a href="/security">Security</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
  </div><!-- /.site-footer -->
</div><!-- /.container -->


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-suggester-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="fullscreen-contents js-fullscreen-contents" placeholder=""></textarea>
      <div class="suggester-container">
        <div class="suggester fullscreen-suggester js-suggester js-navigation-container"></div>
      </div>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-x flash-close js-ajax-error-dismiss" aria-label="Dismiss error"></a>
      Something went wrong with that request. Please try again.
    </div>


      <script crossorigin="anonymous" src="https://assets-cdn.github.com/assets/frameworks-996268c2962f947579cb9ec2908bd576591bc94b6a2db184a78e78815022ba2c.js"></script>
      <script async="async" crossorigin="anonymous" src="https://assets-cdn.github.com/assets/github-91b5990846cbffd53853eab465382e06f3298f588ef744cc4a4400ab977c2b82.js"></script>
      
      

  </body>
</html>

